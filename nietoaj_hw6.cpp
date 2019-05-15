#define MYSQLPP_MYSQL_HEADERS_BURIED
#include <mysql++/mysql++.h>
#include <string>
#include <cstring>
#include <iostream>
#include <fstream>
#include <vector>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string.hpp>

void readingAndWriting(std::string in, std::string out) {
    std::ifstream Filein(in);
    std::ofstream Fileout(out);
    mysqlpp::Connection myDB("cse278", "192.155.95.213", "cse278", "zLATBsMFQhwXdNbr");
    std::string line;
    while (getline(Filein, line)) {
        mysqlpp::Query query = myDB.query();
        query << line;
        try {
            query.parse();
            mysqlpp::StoreQueryResult result = query.store();
            Fileout << query << "\n";
            mysqlpp::StoreQueryResult::const_iterator iterator;
            for (iterator = result.begin(); iterator != result.end(); iterator++) {
                mysqlpp::Row row = *iterator;
                Fileout << row[0];
                for (int i = 1; i < row.size(); i++) {
                    Fileout << " | " << row[i];
                }
                Fileout << "\n";
            }
            /* feedback it worked - format on assignment */
        }// Prevents crashing for malformed queries        
        catch (mysqlpp::BadQuery e) {
            std::cerr << "Query: " << line << std::endl;
            std::cerr << "Query is not correct SQL syntax" << std::endl;
        } catch (mysqlpp::BadParamCount e) {
            std::cerr << query.width() << std::endl;
        }
        Fileout << "\n";
    }
    Filein.close();
    Fileout.close();
}

void createTable(std::string path) {
    //Connect to database
    mysqlpp::Connection myDB("cse278", "192.155.95.213", "cse278", "zLATBsMFQhwXdNbr");
    int count = 0;
    //Important variables
    std::string line;
    std::string qString = "";
    std::string primary = "";
    std::ifstream file(path);
    std::vector<std::string> majorVec;
    std::vector<std::string> minorVec;
    while (getline(file, line)) {
        majorVec.clear();
        mysqlpp::Query query = myDB.query();
        qString = "CREATE TABLE ";
        boost::algorithm::split(majorVec, line, boost::is_any_of(","));
        qString.append(majorVec[0].substr(majorVec[0].find_first_of(":") + 1));
        qString.append("(");
        for (int i = 1; i < majorVec.size(); i++) {
            boost::algorithm::split(minorVec, majorVec[i], boost::is_any_of(":"));
            qString.append(minorVec[0] + " ");
            boost::to_upper(minorVec[1]);
            qString.append(minorVec[1] + " ");
            if (majorVec[i].find("not_null") != -1) {
                qString.append("NOT NULL,");
            } else {
                qString.append(",");
            }
            if (majorVec[i].find("key") != -1 && primary == "") {
                primary = minorVec[0];
            }
        }
        if (qString != "") {
            qString.append("PRIMARY KEY (" + primary + "));");
        } else {
            qString.append(");");
        }
        query << qString;
        try {
            query.parse();
            mysqlpp::StoreQueryResult result = query.store();
            std::cout << "Table " << majorVec[0].substr(majorVec[0].find_first_of(":") + 1) << " Created" << std::endl;
        } catch (mysqlpp::BadQuery e) {
            std::cerr << "Query: " << line << std::endl;
            std::cerr << "Query is not correct SQL syntax" << std::endl;
        } catch (mysqlpp::BadParamCount e) {
            std::cerr << query.width() << std::endl;
        }
    }
    file.close();
}

void dropTable(std::string table) {
    //Connect to the database
    mysqlpp::Connection myDB("cse278", "192.155.95.213", "cse278", "zLATBsMFQhwXdNbr");
    mysqlpp::Query query = myDB.query();
    query << "DROP TABLE " << table << ";";
    try {
        query.parse();
        mysqlpp::StoreQueryResult result = query.store();
        std::cout << "Table " << table << " Dropped" << std::endl;
    } catch (mysqlpp::BadQuery e) {
        std::cerr << "Query is not correct SQL syntax" << std::endl;
    } catch (mysqlpp::BadParamCount e) {
        std::cerr << query.width() << std::endl;
    }
}

void interactive() {
    //Connect to database
    mysqlpp::Connection myDB("cse278", "192.155.95.213", "cse278", "zLATBsMFQhwXdNbr");
    std::string qString;
    //Read user input until enters "quit"
    while (getline(std::cin, qString) && qString != "quit") {
        mysqlpp::Query query = myDB.query();
        query << qString;
        try {
            query.parse();
            mysqlpp::StoreQueryResult result = query.store();
            std::cout << "-----Query Result-----" << std::endl;
            //Following iterates through result and formats and prints.
            mysqlpp::StoreQueryResult::const_iterator iterator;
            for (iterator = result.begin(); iterator != result.end(); iterator++) {
                mysqlpp::Row row = *iterator;
                std::cout << "| " << row[0];
                for (int i = 1; i < row.size(); i++) {
                    std::cout << " | " << row[i];
                }
                std::cout << "\n";
            }
            std::cout << "------End Result------" << std::endl;
        } catch (mysqlpp::BadQuery e) {
            std::cerr << "Query: " << qString << std::endl;
            std::cerr << "Query is not correct SQL syntax" << std::endl;
        }
    }
}

void loadData(std::string& path) {
    //Create database and connect to file
    std::ifstream file(path);
    mysqlpp::Connection myDB("cse278", "192.155.95.213", "cse278", "zLATBsMFQhwXdNbr");
    //key variables
    std::string line;
    std::string qString = "";
    std::string columns = "";
    std::string values = "";
    int count = 0;
    std::vector<std::string> vec;
    while (getline(file, line)) {
        qString = "";
        columns = "(";
        values = "VALUES (";
        mysqlpp::Query query = myDB.query();
        boost::algorithm::split(vec, line, boost::is_any_of(","));
        qString.append("INSERT INTO " + vec[0] + " ");
        for (int i = 1; i < vec.size(); i++) {
            columns.append(vec[i].substr(0, vec[i].find_first_of(":")));
            values.append(vec[i].substr(vec[i].find_first_of(":") + 1));
            if (i < vec.size() - 1) {
                columns.append(", ");
                values.append(", ");
            }
        }
        qString.append(columns + ") ");
        qString.append(values + ");");
        query << qString;
        try {
            query.parse();
            mysqlpp::StoreQueryResult result = query.store();
            count++;
            std::cout << "Data Line " << count << " Loaded" << std::endl;
        } catch (mysqlpp::BadQuery e) {
            std::cerr << "Query: " << line << std::endl;
            std::cerr << "Query is not correct SQL syntax" << std::endl;
        } catch (mysqlpp::BadParamCount e) {
            std::cerr << query.width() << std::endl;
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc < 1 || argc > 4) {
        std::cerr << "Invalid number of arguments" << std::endl;
        return 1;
    }
    std::string option = argv[1];
    //Interactive mode
    if (option == "-I") {
        interactive();
        //Load data in
    } else if (option == "-L") {
        //Check to ensure files are legitimate and can be found
        std::string path = argv[2];
        std::ifstream File(path);
        if (!File.good()) {
            std::cerr << "File could not be found or read" << std::endl;
            return 3;
        }
        File.close();
        loadData(path);
        //Read and Write SQL Statements and results
    } else if (option == "-W") {
        //Check to ensure files are legitimate and can be found
        std::string in = argv[2];
        std::string out = argv[3];
        std::ifstream Filein(in);
        std::ofstream Fileout(out);
        if (!Filein.good() || !Fileout.good()) {
            std::cerr << "File(s) were not located" << std::endl;
            return 3;
        }
        Filein.close();
        Fileout.close();
        readingAndWriting(argv[2], argv[3]);
        //Drop table from database
    } else if (option == "-D") {
        dropTable(argv[2]);
        //CREATE TABLE 
    } else if (option == "-C") {
        //Check to ensure files are legitimate and can be found
        std::string path = argv[2];
        std::ifstream File(path);
        if (!File.good()) {
            std::cerr << "File could not be found or read" << std::endl;
            return 3;
        }
        File.close();
        createTable(path);
        //Input is not according to specifications
    } else {
        std::cerr << "Invalid input" << std::endl;
        return 2;
    }
    return 0;
}
