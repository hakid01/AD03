/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author erifc
 */
public class DB {

    public DB() {
    }
    private static String sqlCustomers = "CREATE TABLE IF NOT EXISTS customers (\n"
            + "customer_id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "name text NOT NULL,\n"
            + "surname text NOT NULL,\n"
            + "email text NOT NULL UNIQUE\n"
            + ");";

    private static String sqlEmployees = "CREATE TABLE IF NOT EXISTS employees (\n"
            + "employee_id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "name text NOT NULL,\n"
            + "surname text NOT NULL\n"
            + ");";

    private static String sqlItems = "CREATE TABLE IF NOT EXISTS items (\n"
            + "item_id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "name text NOT NULL,\n"
            + "description text NOT NULL,\n"
            + "price REAL NOT NULL \n"
            + ");";

    private static String sqlProvinces = "CREATE TABLE IF NOT EXISTS provinces (\n"
            + "province_id integer PRIMARY KEY ON CONFLICT IGNORE,\n"
            + "name text NOT NULL \n"
            + ");";

    private static String sqlStores = "CREATE TABLE IF NOT EXISTS stores (\n"
            + "store_id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "name text NOT NULL,\n"
            + "province_id text NOT NULL,\n"
            + "town text NOT NULL,\n"
            + "FOREIGN KEY (province_id) REFERENCES provinces (province_id) \n"
            + "ON DELETE CASCADE \n"
            + "ON UPDATE NO ACTION \n"
            + ");";

    private static String sqlStockItemStore = "CREATE TABLE IF NOT EXISTS item_store (\n"
            + "store_id INTEGER,\n"
            + "item_id INTEGER,\n"
            + "stock INTEGER NOT NULL DEFAULT '0',\n"
            + "PRIMARY KEY (store_id, item_id),\n"
            + "FOREIGN KEY (store_id) REFERENCES stores (store_id) \n"
            + "ON DELETE CASCADE \n"
            + "ON UPDATE NO ACTION,\n"
            + "FOREIGN KEY (item_id) REFERENCES items (item_id) \n"
            + "ON DELETE CASCADE \n"
            + "ON UPDATE NO ACTION \n"
            + ");";

    private static String sqlHoursEmployeeStore = "CREATE TABLE IF NOT EXISTS employee_store (\n"
            + "store_id INTEGER,\n"
            + "employee_id INTEGER,\n"
            + "hours INTEGER NOT NULL DEFAULT '0',\n"
            + "PRIMARY KEY (store_id, employee_id),\n"
            + "FOREIGN KEY (store_id) REFERENCES stores (store_id) \n"
            + "ON DELETE CASCADE \n"
            + "ON UPDATE NO ACTION,\n"
            + "FOREIGN KEY (employee_id) REFERENCES employees (employee_id) \n"
            + "ON DELETE CASCADE \n"
            + "ON UPDATE NO ACTION \n"
            + ");";

    public static void createDatabase(String filename) {
        String databaseFile = "jdbc:sqlite:" + filename;

        try {
            Connection connection = DriverManager.getConnection(databaseFile);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("A base de datos foi creada");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection connectDatabase(String filename) {
        Connection connection = null;
        try {
            //Establecemos integridad de claves foránea a true
            Properties propiedadesConex=new Properties();
            propiedadesConex.setProperty("foreign_keys", "true");        
            //Creamos a conexión a base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:" + filename, propiedadesConex);
            System.out.println("Conexión realizada con éxito");
            return connection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void desconnetDatabase(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Desconexión realizada con éxito");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTable(Connection con, String sqlTable) {
        try {
            String sql = sqlTable;

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Table has been successfully created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //********* Metodos para insertar datos ************
    public static void insertStore(Connection con, String name, String provinceId, String town) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO stores(name, province_id, town) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, provinceId);
            pstmt.setString(3, town);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertProvince(Connection con, int id, String name) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO provinces(province_id, name) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCustomer(Connection con, String name, String surname, String email) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO customers(name, surname, email) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertEmployee(Connection con, String name, String surname) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO employees(name, surname) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertItem(Connection con, String name, String description, double price) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO items(name, description, price) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertItemStore(Connection con, int store_id, int item_id, int stock) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO item_store(store_id, item_id, stock) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, store_id);
            pstmt.setInt(2, item_id);
            pstmt.setInt(3, stock);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertEmployeeStore(Connection con, int store_id, int employee_id, int hours) {
        try {
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO employee_store(store_id, employee_id, hours) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setInt(1, store_id);
            pstmt.setInt(2, employee_id);
            pstmt.setInt(3, hours);
            pstmt.executeUpdate();
            System.out.println("Data successfully added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //********* Metodos para consultas ************
    public static ArrayList<String> getProvinces(Connection con) {

        ArrayList<String> provinces = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from provinces order by province_id");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                String province_id = rs.getString("province_id");
                String name = rs.getString("name");
                System.out.println("Provincia: " + province_id + " " + name);
                provinces.add(name);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return provinces;
    }

    public static ArrayList<String> getCustomers(Connection con) {

        ArrayList<String> customers = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from customers");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int id = rs.getInt("customer_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                System.out.println("Cliente: " + name + " " + surname + " -> " + email);
                customers.add("" + id + " - " + name + " " + surname + " -> " + email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customers;
    }
    
    public static ArrayList<String> getEmailsCustomers(Connection con) {

        ArrayList<String> emailCustomers = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select email from customers");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                String email = rs.getString("email");
                System.out.println("Cliente email: " + email);
                emailCustomers.add(email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return emailCustomers;
    }

    public static ArrayList<String> getEmployees(Connection con) {

        ArrayList<String> employees = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from employees");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int id = rs.getInt("employee_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                System.out.println("Id: " + id + "Cliente: " + name + " " + surname);
                employees.add("" + id + " - " + name + " " + surname);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    public static ArrayList<String> getStores(Connection con) {

        ArrayList<String> stores = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select s.store_id, s.name, s.town, p.name "
                    + "from stores s, provinces p where s.province_id = p.province_id");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int store_id = rs.getInt(1);
                String name = rs.getString(2);
                String town = rs.getString(3);
                String province = rs.getString(4);
                System.out.println("Tienda: " + store_id + " - " + name + ", " + town + " -> " + province);
                stores.add("" + store_id + " - " + name + ", " + town + " (" + province + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return stores;
    }

    public static ArrayList<String> getItems(Connection con) {

        ArrayList<String> items = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from items");
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                System.out.println("Producto: " + id + " - " + name + " " + description + " -> " + price);
                items.add("" + id + " - " + name + " -> " + price + "€." + description + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return items;
    }

    public static ArrayList<String> getItemsStore(Connection con, int store_id) {

        ArrayList<String> items = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select i.name, i.item_id, x.stock from items i, item_store x "
                    + "where i.item_id = x.item_id and x.store_id = " + store_id);
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                String name = rs.getString(1);
                int item_id = rs.getInt(2);
                int stock = rs.getInt(3);
                System.out.println("Id: " + item_id + " Producto: " + name + " - Stock: " + stock);
                items.add("" + item_id + " - " + name + " - Stock: " + stock);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return items;
    }

    public static ArrayList<String> getIdsItemsStore(Connection con, int store_id) {

        ArrayList<String> idsItems = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select i.item_id from items i, item_store x "
                    + "where i.item_id = x.item_id and x.store_id = " + store_id);
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas

                int item_id = rs.getInt(1);

                System.out.println("Id: " + item_id );
                idsItems.add("" + item_id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idsItems;
    }

    public static ArrayList<String> getEmployeesStore(Connection con, int store_id) {

        ArrayList<String> employees = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select e.employee_id, e.name, e.surname, x.hours "
                    + "from employees e, employee_store x "
                    + "where e.employee_id = x.employee_id and x.store_id = " + store_id);
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int employee_id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                int hours = rs.getInt(4);
                System.out.println("Id: " + employee_id + " Empleado: " + name + " " + surname + " - Horas: " + hours);
                employees.add("" + employee_id + " - " + name + " " + surname + " - Horas: " + hours);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    public static ArrayList<String> getIdsEmployeesStore(Connection con, int store_id) {

        ArrayList<String> idsEmployees = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select e.employee_id "
                    + "from employees e, employee_store x "
                    + "where e.employee_id = x.employee_id and x.store_id = " + store_id);
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int employee_id = rs.getInt(1);
                System.out.println("Id: " + employee_id);
                idsEmployees.add("" + employee_id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idsEmployees;
    }

    public static ArrayList<String> getStoresEmployee(Connection con, int employee_id) {

        ArrayList<String> stores = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select s.store_id, s.name, s.town, p.name "
                    + "from stores s, employee_store x, provinces p "
                    + "where s.store_id = x.store_id and p.province_id = s.province_id and x.employee_id = " + employee_id);
            System.out.println("antes del while");
            while (rs.next()) {
                //imprimimos o nome de todolas persoas
                int store_id = rs.getInt(1);
                String store_name = rs.getString(2);
                String store_town = rs.getString(3);
                String province_name = rs.getString(4);
                System.out.println("Id: " + store_id + " Empleado: " + store_name + " " + store_town + " - Horas: " + province_name);
                stores.add("" + store_id + " - " + store_name + ". Ciudad: " + store_town + ". Provincia: " + province_name);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return stores;
    }

    //*********** Métdodos para borrar datos *************
    public static void deleteCustormer(Connection con, String email) {
        try {
            String sql = "DELETE FROM customers WHERE email = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            System.out.println("Cliente borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteEmployee(Connection con, int idSelectedEmployee) {
        try {
            String sql = "DELETE FROM employees WHERE employee_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idSelectedEmployee);
            pstmt.executeUpdate();
            System.out.println("Empleado borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteItem(Connection con, int id) {
        try {
            String sql = "DELETE FROM items WHERE item_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Producto borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteItemStore(Connection con, int store_id, int item_id) {
        try {
            String sql = "DELETE FROM item_store WHERE store_id = ? and item_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, store_id);
            pstmt.setInt(2, item_id);
            pstmt.executeUpdate();
            System.out.println("Producto borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteEmployeeStore(Connection con, int store_id, int employee_id) {
        try {
            String sql = "DELETE FROM employee_store WHERE store_id = ? and employee_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, store_id);
            pstmt.setInt(2, employee_id);
            pstmt.executeUpdate();
            System.out.println("Empleado borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteStore(Connection con, int id) {
        try {
            String sql = "DELETE FROM stores WHERE store_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Producto borrado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //*********** Metodos para actualizar datos ***********
    public static void updateStockItem_Store(Connection con, int newStock, int store_id, int item_id) {
        try {
            String sql = "UPDATE item_store SET stock = ? "
                    + "WHERE store_id = ? AND item_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, store_id);
            pstmt.setInt(3, item_id);
            pstmt.executeUpdate();
            System.out.println("Stock actualizado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void updateHoursEmployee_Store(Connection con, int newHours, int store_id, int employee_id) {
        try {
            String sql = "UPDATE employee_store SET hours = ? "
                    + "WHERE store_id = ? AND employee_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newHours);
            pstmt.setInt(2, store_id);
            pstmt.setInt(3, employee_id);
            pstmt.executeUpdate();
            System.out.println("Número de horas semanales actualizado con éxito");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //*********** Getters para generar tablas *************
    public static String getSqlEmployees() {
        return sqlEmployees;
    }

    public static String getSqlCustomers() {
        return sqlCustomers;
    }

    public static String getSqlItems() {
        return sqlItems;
    }

    public static String getSqlProvinces() {
        return sqlProvinces;
    }

    public static String getSqlStores() {
        return sqlStores;
    }

    public static String getSqlStockItemStore() {
        return sqlStockItemStore;
    }

    public static String getSqlHoursEmployeeStore() {
        return sqlHoursEmployeeStore;
    }

}
