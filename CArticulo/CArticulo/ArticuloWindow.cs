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
            ComboBoxHelper.Fill(comboBoxCategoria, "select id, nombre from categoria order by nombre", articulo.Categoria);

			saveAction.Activated += delegate {
                articulo.Nombre = entryNombre.Text;
                articulo.Precio = (decimal)spinnButtonPrecio.Value;
                articulo.Categoria = Convert.ToInt64(ComboBoxHelper.GetId(comboBoxCategoria));
                ArticuloDao.Save(articulo);
				Destroy();
			};
		}
	}
}