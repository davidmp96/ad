﻿using Gtk;
using MySql.Data.MySqlClient;
using System;
using System.Data;
using CArticulo;
using Serpis.Ad;

public partial class MainWindow : Gtk.Window
{
	public MainWindow() : base(Gtk.WindowType.Toplevel)
	{
		Build();
		Title = "Articulo";
		deleteAction.Sensitive = false;
		editAction.Sensitive = false;

		string connectionString = "server=localhost;database=dbprueba;user=root;password=sistemas";
		App.Instance.Connection = new MySqlConnection(connectionString);
		App.Instance.Connection.Open();

		TreeViewHelper.Fill(treeView, ArticuloDao.SelectAll);

		treeView.Selection.Changed += delegate {
			bool hasSelected = treeView.Selection.CountSelectedRows() > 0;
			deleteAction.Sensitive = hasSelected;
			editAction.Sensitive = hasSelected;
		};

		newAction.Activated += delegate {
			Articulo articulo = new Articulo();
			new ArticuloWindow(articulo);
		};

		editAction.Activated += delegate {
			object id = TreeViewHelper.getId(treeView);
			Articulo articulo = ArticuloDao.Load(id);
			new ArticuloWindow(articulo);
		};

		refreshAction.Activated += delegate {
			TreeViewHelper.Fill(treeView, ArticuloDao.SelectAll);
		};


		deleteAction.Activated += delegate {
			if (WindowHelper.Confirm(this, "¿Quieres eliminar el registro?"))
			{
				object id = TreeViewHelper.getId(treeView);
				ArticuloDao.Delete(id);
			}
		};

	}

	protected void OnDeleteEvent(object sender, DeleteEventArgs a)
	{
        App.Instance.Connection.Close();
        Application.Quit();
		a.RetVal = true;
	}
}