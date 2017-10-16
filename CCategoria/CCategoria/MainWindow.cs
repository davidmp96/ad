using Gtk;
using MySql.Data.MySqlClient;
using System;
using System.Data;
using CCategoria;

public partial class MainWindow : Gtk.Window
{

    public MainWindow() : base(Gtk.WindowType.Toplevel)
    {

        Build();
        //CONEXION CON LA BASE DE DATOS
        string connectionString = "server=localhost;database=dbprueba;user=root;password=sistemas";
        App.Instance.Connection = new MySqlConnection(connectionString);
        App.Instance.Connection.Open();

		//AÑADIMOS COLUMNAS AL TREEVIEW
		treeView.AppendColumn("id", new CellRendererText(), "text", 0);
		treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
		ListStore listStore = new ListStore(typeof(string), typeof(string));
		treeView.Model = listStore;
        //listStore.AppendValues("1", "cat. 1"); FORMA MANUAL DE INTRODUCIR
        //listStore.AppendValues("2", "cat. 2"); LOS DATOS EN LA TABLA.

        fillListStore(listStore);

        //CÓDIGO INTRODUCIDO EN EL BOTÓN NUEVA
        newAction.Activated += delegate {
            new CategoriaWindow();
        };

        //CÓDIGO INTRODUCIDO EN EL BOTÓN REFRESH LLAMANDO AL MÉTODO refactorizado fillListStore
        refreshAction.Activated += delegate {
            fillListStore(listStore);
		};
    }

    private void fillListStore(ListStore listStore) {
        listStore.Clear();
		IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
		dbCommand.CommandText = "select * from categoria order by id";
		IDataReader dataReader = dbCommand.ExecuteReader();
		while (dataReader.Read()) 
			listStore.AppendValues(dataReader["id"].ToString(), dataReader["nombre"]);
		dataReader.Close();
    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        App.Instance.Connection.Close();
        Application.Quit();
        a.RetVal = true;
    }
}