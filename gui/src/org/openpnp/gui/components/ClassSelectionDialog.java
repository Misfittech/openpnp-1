package org.openpnp.gui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

public class ClassSelectionDialog<T> extends JDialog {
	private Class<? extends T> selectedClass;
	private JList list;
	
	public ClassSelectionDialog(
			Frame parent,
			String title,
			String description,
			List<Class<? extends T>> classes) {
		super(parent, title, true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(8, 8, 4, 8));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelActions = new JPanel();
		panel.add(panelActions, BorderLayout.SOUTH);
		panelActions.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnCancel = new JButton(cancelAction);
		panelActions.add(btnCancel);
		
		JButton btnSelect = new JButton(selectAction);
		panelActions.add(btnSelect);
		
		JLabel lblDescription = new JLabel("Please select an implemention class from the given list. Or whatever.");
		lblDescription.setBorder(new EmptyBorder(4, 4, 8, 4));
		panel.add(lblDescription, BorderLayout.NORTH);
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblDescription.setText(description);
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(list, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(parent);
		
		DefaultListModel listModel = new DefaultListModel();
		list.setModel(listModel);
		for (Class<? extends T> clz : classes) {
			listModel.addElement(new ClassListItem<T>(clz));
		}
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				selectAction.setEnabled(ClassSelectionDialog.this.list.getSelectedValue() != null);
			}
		});
		
		selectAction.setEnabled(false);
	}
	
	private Action selectAction = new AbstractAction("Accept") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			selectedClass = (Class<? extends T>) list.getSelectedValue();
			setVisible(false);
		}
	};
	
	private Action cancelAction = new AbstractAction("Cancel") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	};
	
	public Class<? extends T> getSelectedClass() {
		return selectedClass;
	}
	
	private class ClassListItem<T1> {
		private Class<? extends T1> clz;
		
		public ClassListItem(Class<? extends T1> clz) {
			this.clz = clz;
		}
		
		@Override
		public String toString() {
			return clz.getSimpleName();
		}
	}
}