using Gtk;
using System;
using System.Data;

namespace Serpis.Ad
                
{
    public class ComboBoxHelper
    {
        public const string NullLabel = "<sin asignar>";
        public static void Fill(ComboBox comboBox, string selectSql, object id) {
			IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
			dbCommand.CommandText = selectSql;
			IDataReader dataReader = dbCommand.ExecuteReader();
            init(comboBox);
            fill(comboBox, dataReader, id);
            dataReader.Close();
        }

        private static void init(ComboBox comboBox) {
			CellRendererText cellRendererText = new CellRendererText();
			comboBox.PackStart(cellRendererText, false);
			comboBox.AddAttribute(cellRendererText, "text", 1);
			ListStore listStore = new ListStore(typeof(string), typeof(string));
			comboBox.Model = listStore;
        }

        private static void fill(ComboBox comboBox, IDataReader dataReader, object id) {
			id = id.ToString();
            ListStore listStore = (ListStore)comboBox.Model;

			TreeIter initialTreeIter = listStore.AppendValues("0", NullLabel);
			while (dataReader.Read()) {
				TreeIter treeIter = listStore.AppendValues(dataReader[0].ToString(), dataReader[1].ToString());
				if (id.Equals(dataReader[0].ToString()))
					initialTreeIter = treeIter;
			}
			comboBox.SetActiveIter(initialTreeIter);
        }

        public static object GetId(ComboBox comboBox) {
            TreeIter treeiter;
            comboBox.GetActiveIter(out treeiter);
            return comboBox.Model.GetValue(treeiter, 0);
        }
    }
}