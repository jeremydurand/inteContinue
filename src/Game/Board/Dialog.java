package Game.Board;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Game.GameManager;

public class Dialog {
	public static JDialog returnDialog(JOptionPane optionPane, String title) {
		final JDialog dialog = new JDialog(GameManager.getInstance().getBoard(), title, true);
		dialog.setLocationRelativeTo(GameManager.getInstance().getBoard());
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				//setLabel("Thwarted user attempt to close window.");
			}
		});

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();

				if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
					// If you were going to check something
					// before closing the window, you'd do
					// it here.
					dialog.setVisible(false);
				}
			}
		});
		dialog.pack();
		dialog.setVisible(true);
		return dialog;
	}
}
