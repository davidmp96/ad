using System;
using System.Data;
using Serpis.Ad;

namespace CArticulo
{
	public class ArticuloDao
	{
		public const string SelectAll = "select * from articulo order by id";

		public static Articulo Load(object id)
		{
			IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
			dbCommand.CommandText = "select * from articulo where id = @id";
			DbCommandHelper.AddParameter(dbCommand, "id", id);
			IDataReader dataReader = dbCommand.ExecuteReader();
			dataReader.Read(); //TODO tratamiento de excepciones
			string nombre = (string)dataReader["nombre"];
			decimal precio = (decimal)dataReader["precio"];
			long categoria = (long)dataReader["categoria"];
			Articulo articulo = new Articulo();
			articulo.Id = Convert.ToInt64(id);
			articulo.Nombre = nombre;
			articulo.Categoria = categoria;
			articulo.Precio = precio;
			dataReader.Close();

			return articulo;
		}

		public static void Save(Articulo articulo)
		{

			if (articulo.Id == 0)
			{
				insert(articulo);
			}
			else
			{
				update(articulo);
			}

		}

		private static void insert(Articulo articulo)
		{
			IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
			dbCommand.CommandText = "insert into articulo (nombre, precio, categoria) " +
			"values (@nombre, @precio, @categoria)";
			DbCommandHelper.AddParameter(dbCommand, "nombre", articulo.Nombre);
			DbCommandHelper.AddParameter(dbCommand, "precio", articulo.Precio);
			DbCommandHelper.AddParameter(dbCommand, "categoria", articulo.Categoria);
			dbCommand.ExecuteNonQuery();

		}
		private static void update(Articulo articulo)
		{
			IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
			dbCommand.CommandText = "update articulo set nombre = @nombre, " +
			"precio = @precio, categoria = @categoria where id = @id;";
			DbCommandHelper.AddParameter(dbCommand, "nombre", articulo.Nombre);
			DbCommandHelper.AddParameter(dbCommand, "precio", articulo.Precio);
			DbCommandHelper.AddParameter(dbCommand, "categoria", articulo.Categoria);
			DbCommandHelper.AddParameter(dbCommand, "id", articulo.Id);
			dbCommand.ExecuteNonQuery();

		}
		public static void Delete(object id)
		{
			IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
			dbCommand.CommandText = "delete from articulo where id= @id;";
			DbCommandHelper.AddParameter(dbCommand, "id", id);
			dbCommand.ExecuteNonQuery();
		}
	}
}