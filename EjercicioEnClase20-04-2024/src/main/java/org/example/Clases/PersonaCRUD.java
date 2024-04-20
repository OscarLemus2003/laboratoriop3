package org.example.Clases;


import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;


public class PersonaCRUD {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    public void conexion(){
        //cadena de conezion, contiene la informacion de la instalacion de mogodb
        ConnectionString connecctionString = new ConnectionString("mongodb://localhost:27017");

        //se crean las configuraciones especificas para conezion y manejo de la db
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connecctionString)
                .retryWrites(true)
                .build();
        //crear la conexion y establece la comunicacion
        mongoClient = MongoClients.create(settings);

        database = mongoClient.getDatabase("PersonasCrud");
        collection = database.getCollection("Personas");
        System.out.println("Conexion establecida");
    }

    //crear un usuario (insert)
    public void crearPersona(){
//    mdUsuario usuario = new mdUsuario();
//    usuario.setNombre("Juan");
//    usuario.setCorreo("juanitoElVrgas@gmail.com");
//    usuario.setIdTelegram(1234567890);

        Personas usuario = new Personas();
        usuario.setNombre("Mariita");
        usuario.setCiudad("Peru");
        usuario.setEdad(22);




        Document document = new Document("nombre", usuario.getNombre())
                .append("ciudad", usuario.getCiudad())
                .append("edad", usuario.getEdad());
        collection.insertOne(document);
        System.out.println("Persona creada");
    }

    public void leerPersonas(){
        List<Personas> personas = new ArrayList<>();

        //leer todos los documentos
        for (Document doc: collection.find()){
            Personas p = new Personas();
            p.setNombre(doc.getString("nombre"));
            p.setCiudad(doc.getString("ciudad"));
            p.setEdad(doc.getInteger("edad"));
            personas.add(p);
        }
        for (Personas p: personas){
            System.out.println("Nombre: "+p.getNombre());
            System.out.println("Ciudad: "+p.getCiudad());
            System.out.println("Edad: "+p.getEdad());
            System.out.println("_________________________");
        }
    }

    public void actualizarPersona() {
        collection.updateOne(Filters.eq("nombre", "Mariita"),
                Updates.set("ciudad", "Venezuela"));
        System.out.println("Usuario actualizado");
    }

    public  void eliminarPersona() {
        collection.deleteOne(Filters.eq("nombre", "Mariita"));
        System.out.println("Persona eliminada");
    }

}
