using System;

namespace CArticulo
{
    public class Articulo
    {
        public Articulo()
        {
        }

		private long id;
		private string nombre;

		public long Id
		{
			get { return id; }
			set { id = value; }
		}

		public string Nombre
		{
			get { return nombre; }
			set { nombre = value; }
		}

        public decimal Precio { get; set; }
        public long Categoria { get; set; }
    }
}
