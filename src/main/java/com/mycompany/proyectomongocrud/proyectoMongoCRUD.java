/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectomongocrud;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class proyectoMongoCRUD {

    public static void main(String[] args) {
        //Creamos una conexion para la conexion de tipo mongoDb
        MongoClient mongo = crearConexion();

        // Si no existe la base de datos la creamos o se habilita 
        if (mongo != null) {
            DB db = mongo.getDB("CRUD");
            System.out.println("Base de datos creada o habilitada");

            //Creamos una coleccion(TABLA) si no existe se crea 
            //inserta el documento (REGISTRO) a la coleccion
            System.out.println("Insertando documentos de usuarios");
            insertarUsuario(db, "usuarios", "Sergio", "Mexico");
            insertarUsuario(db, "usuarios", "Luara", "Colombia");
            insertarUsuario(db, "usuarios", "Franco", "Chile");
            insertarUsuario(db, "usuarios", "Armando", "Venezuela");

            //Mostramos los documentos de la coleccion
            System.out.println("\n\nSe muestarn todos los datos de la coleccion Usuarios");
            mostrarColeccion(db, "usuarios");

            //Buscamos a un elemento de la coleccion, en este caso a sergioy mostraremos sus datos
            System.out.println("\n\nSe mustran los datos que coincidan con usuario sergio");
            buscarPorNombre(db, "usuarios", "Sergio");

            System.out.println("\n\nAntes del update, la coleccion, buscar a sergio y su pais");
            mostrarColeccion(db, "usurios");
            actualizarDocumento(db, "usuarios", "Sergio", "Peru");
            System.out.println("\n\nDespues del update");
            mostrarColeccion(db, "usuarios");

            System.out.println("\n\nAntes del delete vemos los usuarios de colombia");
            mostrarColeccion(db, "usuarios");
            borrarDocumento(db, "usuarios", "Colombia");
            System.out.println("despues del delete");
            mostrarColeccion(db, "usuarios");
        }
    }

    //Metodo para crear la conexion a mongoDb
    public static MongoClient crearConexion() {
        System.out.println("PRUEBA CONEXION MONGODB");

        MongoClient mongo = null;
        mongo = new MongoClient("localhost", 27017);

        return mongo;
    }

    public static void insertarUsuario(DB db, String coleccion, String nombre, String pais) {

        //Creamos unobjeto de tipo Collectin(O se activa), con el nombre de la coleccion enviada
        DBCollection colec = db.getCollection(coleccion);

        //Creamos y preparamos el documento a insertar con la informacion recibida
        BasicDBObject documento = new BasicDBObject();
        documento.put("nombre", nombre);
        documento.put("pais", pais);

        //Se realiza la inserccion
        colec.insert(documento);

    }

    public static void mostrarColeccion(DB db, String coleccion) {

        //Creamos un objeto de tipo coleccion(o se activa), con el nombre de la coleccion
        DBCollection colec = db.getCollection(coleccion);

        //Creamos un cursor para trabajar las respuestas de las busquedas
        DBCursor cursor = colec.find(); // Nos regresa el apuntador/ cursor al primer elemnto de la coleccion

        while (cursor.hasNext()) {
            System.out.println("* " + cursor.next().get("nombre") + " - "
                    + cursor.curr().get("pais"));
        }
    }

    //Mustran todos los documentos de la colleccion de usuario que coincidan con el nombre
    public static void buscarPorNombre(DB db, String colection, String nombre) {

        //Creamos un objeto de tipo collection (o se activa), con el nombre de la coleccion enviada
        DBCollection colect = db.getCollection(colection);

        //Creamos un objeto de tipo para la consulta con el campo nombre
        BasicDBObject consulta = new BasicDBObject();
        consulta.put("nombre", nombre);

        //Mediante el usu de el cursor avanzamos sobre los resultados de la busqueda 
        // Y muestra todos los documentos que coincidan con la consulta
        DBCursor cursor = colect.find(consulta);
        while (cursor.hasNext()) {
            System.out.println("-- " + cursor.next().get("nombre") + " - "
                    + cursor.curr().get("pais"));
        }
    }
    
    //Metodo para actualizar un documento (registro)
    public static void actualizarDocumento(DB db, String coleccion, String nombre, String pais){
    
        //Creamos un objeto de tipo coleccion (o se activa) con el nombre de le coleccion enviada
        DBCollection colec = db.getCollection(coleccion);
        
        //Preparamos una sentencia con la informacion a remplazar 
        BasicDBObject actualizarPais = new BasicDBObject();
        
        actualizarPais.append("$set", new BasicDBObject().append("pais", pais));
        //Aun no se hace el cambio solo se preparo
        
        BasicDBObject buscarPorNombre = new BasicDBObject();
        buscarPorNombre.append("nombre", nombre);
        
        //Realiza el update
        
        colec.updateMulti(buscarPorNombre, actualizarPais); //Actualizar pais se requieren objetos
    }
    
    public static void borrarDocumento(DB db, String coleccion, String pais){
    
        //Creamos un objeto de tipo collection(o se activa), con el nombre de la coleccion enviada
        DBCollection colec = db.getCollection(coleccion);
        
        colec.remove(new BasicDBObject().append("nombre pais", pais));
    }
}
