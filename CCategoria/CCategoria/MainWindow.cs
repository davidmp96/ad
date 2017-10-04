using Gtk;
using MySql.Data.MySqlClient;
using System;
using System.Data;

using CCategoria;

public partial class MainWindow : Gtk.Window {
    
    public MainWindow() : base(Gtk.WindowType.Toplevel) {

        Build();
        //CONEXION CON LA BASE DE DATOS
		string connectionString = "server=localhost;database=dbprueba;user=root;password=sistemas";
        App.Instance.Connection = new MySqlConnection(connectionString);
        App.Instance.Connection.Open();

        //SENTENCIA SQL QUE QUEREMOS EJECUTAR PARA LEER DE LA TABLA
        IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
		dbCommand.CommandText = "select * from categoria order by id";
		IDataReader dataReader = dbCommand.ExecuteReader();

        //AÑADIMOS COLUMNAS AL TREEVIEW
		treeView.AppendColumn("id", new CellRendererText(), "text", 0);
		treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
		ListStore listStore = new ListStore(typeof(string), typeof(string));
        treeView.Model = listStore;
		//listStore.AppendValues("1", "cat. 1"); FORMA MANUAL DE INTRODUCIR
		//listStore.AppendValues("2", "cat. 2"); LOS DATOS EN LA TABLA.

        //LEE Y AÑADE LOS DATOS
		while (dataReader.Read()) {
            listStore.AppendValues(dataReader["id"].ToString(), dataReader["nombre"]);
        }
            dataReader.Close();

        //CÓDIGO INTRODUCIDO EN EL BOTÓN
        newAction.Activated += delegate {
            new CategoriaWindow();
        };
    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        App.Instance.Connection.Close();
        Application.Quit();
        a.RetVal = true;
    }
}