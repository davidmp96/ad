using System;
using System.Data;
using Serpis.Ad;

namespace CArticulo
{
	public partial class ArticuloWindow : Gtk.Window
	{

        public ArticuloWindow(Articulo articulo) : base(Gtk.WindowType.Toplevel)
		{
			this.Build();
            entryNombre.Text = articulo.Nombre;
            spinnButtonPrecio.Value = (double)articulo.Precio;

			saveAction.Activated += delegate {
                articulo.Nombre = entryNombre.Text;
                articulo.Precio = (decimal)spinnButtonPrecio.Value;
                ArticuloDao.Save(articulo);
				Destroy();
			};
		}
	}
}