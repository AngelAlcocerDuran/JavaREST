package mx.edu.utez.controller;

import mx.edu.utez.database.ConnectionDB;
import mx.edu.utez.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/employee")
public class Service {

    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();

        try{
            con = ConnectionDB.getConnection();
            String query = "SELECT employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle FROM employees";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                Employee employee = new Employee();
                employee.setEmployeeNumber(rs.getInt("employeeNumber"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setExtension(rs.getString("extension"));
                employee.setEmail(rs.getString("email"));
                employee.setOfficeCode(rs.getInt("officeCode"));
                employee.setReportsTo(rs.getInt("reportsTo"));
                employee.setJobTitle(rs.getString("jobTitle"));

                employees.add(employee);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return employees;
    }

    @POST
    @Path("/{employeeNumber}/{firstName}/{lastName}/{extension}/{email}/{officeCode}/{reportsTo}/{jobTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addEmployee(@PathParam("employeeNumber") int employeeNumber, @PathParam("firstName") String firstName,
                              @PathParam("lastName") String lastName, @PathParam("extension") String extension,
                              @PathParam("email") String email, @PathParam("officeCode") int officeCode,
                              @PathParam("reportsTo") int reportsTo, @PathParam("jobTitle") String jobTitle){
        String result = "";

        try{
            con = ConnectionDB.getConnection();
            String query = "INSERT INTO employees(employeeNumber,lastName,fisrtName,extension,email,officeCode,reportsTo,jobTitle) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, employeeNumber);
            pstm.setString(2, lastName);
            pstm.setString(3, firstName);
            pstm.setString(4, extension);
            pstm.setString(5, email);
            pstm.setInt(6, officeCode);
            pstm.setInt(7, reportsTo);
            pstm.setString(8, jobTitle);
            boolean state = pstm.executeUpdate() == 1;
            if(state){
                result = "Registro Exitoso...";
            }else {
                result = "Registro Fallido...";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return result;
    }

    @PUT
    @Path("/{employeeNumber}/{firstName}/{lastName}/{extension}/{email}/{officeCode}/{reportsTo}/{jobTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateEmployee(@PathParam("employeeNumber") int employeeNumber, @PathParam("firstName") String firstName,
                                 @PathParam("lastName") String lastName, @PathParam("extension") String extension,
                                 @PathParam("email") String email, @PathParam("officeCode") int officeCode,
                                 @PathParam("reportsTo") int reportsTo, @PathParam("jobTitle") String jobTitle){
        String result = "";

        try{
            con = ConnectionDB.getConnection();
            String query = "UPDATE employees SET firstName = ?, lastName = ?, extension = ?, " +
                    "email = ?, officeCode = ?, reportsTo = ?, jobTtitle = ? WHERE employeeNumber = ?)";
            pstm = con.prepareStatement(query);
            pstm.setInt(8, employeeNumber);
            pstm.setString(2, lastName);
            pstm.setString(1, firstName);
            pstm.setString(3, extension);
            pstm.setString(4, email);
            pstm.setInt(5, officeCode);
            pstm.setInt(6, reportsTo);
            pstm.setString(7, jobTitle);
            boolean state = pstm.executeUpdate() == 1;
            if(state){
                result = "Actualizacion Exitosa...";
            }else {
                result = "Actualizacion Fallida...";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return result;
    }

    @DELETE
    @Path("/{employeeNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeEmployee(@PathParam("employeeNumber") int employeeNumber){
        String result = "";

        try{
            con = ConnectionDB.getConnection();
            String query = "DELETE FROM employees WHERE employeeNumber = ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, employeeNumber);
            boolean state = pstm.executeUpdate() == 1;
            if(state){
                result = "Eliminacion Exitosa...";
            }else {
                result = "Eliminacion Fallida...";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return result;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id") int employeeNumber){
        Employee employee = null;
        try{
            con = ConnectionDB.getConnection();
            String query = "SELECT * FROM employees WHERE employeeNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, employeeNumber);
            rs = pstm.executeQuery();
            if(rs.next()){
                employee = new Employee();
                employee.setEmployeeNumber(rs.getInt("employeeNumber"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setExtension(rs.getString("extension"));
                employee.setEmail(rs.getString("email"));
                employee.setOfficeCode(rs.getInt("officeCode"));
                employee.setReportsTo(rs.getInt("reportsTo"));
                employee.setJobTitle(rs.getString("jobTitle"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return employee;
    }

    public void closeConnection(){
        try{
            if(con != null){
                con.close();
            }
            if (pstm != null){
                pstm.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}