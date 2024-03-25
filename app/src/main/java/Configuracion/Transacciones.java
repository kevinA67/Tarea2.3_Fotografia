package Configuracion;

public class Transacciones {

    //Nombre de la base de datos
    public static final String DBName = "PMQuiz";
    //Creación de las tablas de las bases de datos.
    public static final String TableImage = "images";
    //Creacion de los campos de base de datos
    public static final String id = "id";
    public static final String description = "description";
    public static final String image = "image";

    // DDL Create
    public static final String CreateTableImage = "Create table "+ TableImage +"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, image BLOB)";

    //DDL Drop
    public static final String DropTableImage = "DROP TABLE IF EXISTS "+ TableImage;

    //DML
    public static final String SelectAllImage = "SELECT * FROM "+ TableImage;
}
