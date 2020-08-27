package com.techproed;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;

public class DataBaseTesting {
//    JDBC : Java DataBase Connectivity

//     1. Database baglanma
//connection=DriverManager.getConnection(“url”, “kullaniciAdi”, “sifre”);
//     2.Query gonderip data alma
//statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//     3. Bu dataları test caselerde kullanıp assertion yapma.  ResultSet objesiyle datayı kullanırız.
//resultSet = statement.executeQuery("SELECT * FROM Book;”);
//String beklenenDeger = resultSet.getString(“BookName”);
//Assert.assertEquals(beklenenDeger, gercekDeger);

    //Database url
    String url = "jdbc:mysql://107.182.225.121:3306/LibraryMgmt";
    //kullanici adi
    String username = "techpro";
    //sifre
    String password = "tchpr2020";

    //Connection, Statament, ResultSet objelerini olusturalim
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Before
    public void setup() throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    }

    @Test
    public void Test1() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Book;");
        resultSet.next(); // ilk satiri atliyoruz. Cunku baslik ve icinde isimize yarayacak bir data yok.

        String deger1 = resultSet.getString("BookName");
        System.out.println("DEGER1 : " +deger1);

        //TC_02 BookName deki tum degerleri yazdir
        int rowSayisi = 1; // ==> ilk satirda oldugumuzdan 1'den basliyoruz
        while (resultSet.next()){ // bir sonraki deger varsa git, yoksa gitme!
            Object deger2 = resultSet.getObject("BookName");
            System.out.println("DEGER2 : " +deger2);
            rowSayisi++;
        }

        //TC_03 toplam kac 14 satirin olup olmadigini test et
        System.out.println(rowSayisi);
        Assert.assertEquals(14,rowSayisi);

        //TC_04  5. degerin JAVA olup olmadigini test et
        resultSet.absolute(5); // 5.satir
        String deger5 = resultSet.getString("BookName");
        System.out.println("5.DEGER : "+ deger5);
        Assert.assertEquals("JAVA", deger5); //FAIL BUG bulduk ==> cunku 5.satirin degeri JAVA degil Java
//                          Expected :JAVA, Actual :Java
    }

    @Test
    public void Test2() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Book;");

        //TC_05 son degerin UIPath olup olmadigini test et
        resultSet.last(); // ==> dinamik code ile son satira gittik
        String degerSon = resultSet.getString("BookName");
        Assert.assertEquals("UIPath",degerSon);

        //TC_06_ilk satirdaki degerin SQL olup olmadigii test et
        resultSet.first();// ==> dinamik code ile ilk satira gittik   =   resultSet.absolute(1);
        String degerIlk = resultSet.getString("BookName");
        Assert.assertEquals("SQL",degerIlk);
    }

    @Test
    public void Test3() throws SQLException {
//        MetaData : Data ile ilgili bilgiler
        DatabaseMetaData dbMetaData =  connection.getMetaData();
        System.out.println("USERNAME       :"+ dbMetaData.getUserName());
        System.out.println("Database Name  : " + dbMetaData.getDatabaseProductVersion());
        System.out.println("Database Version  : " + dbMetaData.getDatabaseProductName());


    }
}
