package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 frame = new Frame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame1() {
		try {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));
			
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.NORTH);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			File photoFolder = new File("photos");
			String[] photoNames = photoFolder.list();
			
			JComboBox comboBox = new JComboBox(photoNames);
			panel.add(comboBox);
			
			String photoNameSelected = (String) comboBox.getSelectedItem();
			BufferedImage bi = ImageIO.read(new File("photos/"+photoNameSelected));

			PanelPhoto panelPhoto = new PanelPhoto();
			panelPhoto.setBi(bi);
			
			comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						String photoNameSelected = (String) comboBox.getSelectedItem();
						BufferedImage bi = ImageIO.read(new File("photos/"+photoNameSelected));
						panelPhoto.setBi(bi);
						panelPhoto.repaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			});
			
			JButton btnChange = new JButton("Change");
			panel.add(btnChange);
			
			btnChange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					BufferedImage bi = panelPhoto.getBi();
					for(int i=0; i<bi.getWidth();i++) {
						for(int j=0; j<bi.getHeight(); j++) {
							
							bi.setRGB(i, j, bi.getRGB(i,j)/2);
						}
						
					}
					
					panelPhoto.repaint();
				}
				
				
			});
			
			JButton btnSave = new JButton("Save");
			panel.add(btnSave);		
			
			btnSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					BufferedImage bi = panelPhoto.getBi();
					String photoNameSelected = (String) comboBox.getSelectedItem();
					int i = 1;
					File outputPhoto = new File("photos/"+photoNameSelected.substring(0, photoNameSelected.length()-4)+"_"+i+".jpg");
					while(outputPhoto.exists()) {
						i++;
						outputPhoto = new File("photos/"+photoNameSelected.substring(0, photoNameSelected.length()-4)+"_"+i+".jpg");
					}
					
					try {
						ImageIO.write(bi, "jpg", outputPhoto);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
			});
			
			
			
			contentPane.add(panelPhoto, BorderLayout.CENTER);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
