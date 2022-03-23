/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkCheck;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static java.lang.Character.isDigit;
//import com.sun.tools.javac.util.List;

import java.lang.Math;

public class InputFrame extends javax.swing.JFrame {

	public InputFrame() {
		initComponents();
	}

	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new InputFrame().setVisible(true);

			}
		});
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		Link = new javax.swing.JTextField();
		Depth = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(204, 108, 0));

		jLabel1.setFont(new java.awt.Font("Arial Black", 3, 18)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("Link");

		jLabel2.setBackground(new java.awt.Color(0, 255, 0));
		jLabel2.setFont(new java.awt.Font("Arial Black", 3, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("Cut off depth");

		Link.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LinkActionPerformed(evt);
			}
		});

		Depth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DepthActionPerformed(evt);
			}
		});

		jButton1.setText("Enter");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jButton1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
								jPanel1Layout.createSequentialGroup().addGap(30, 30, 30)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150,
														Short.MAX_VALUE)
												.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(55, 55, 55)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(Link).addComponent(Depth,
														javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))))
						.addContainerGap(72, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(89, 89, 89)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(Link, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(38, 38, 38)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(Depth, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
						.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(58, 58, 58)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		String link = Link.getText(); //Gets link from text field.
		String depth = Depth.getText();//Gets depth from text field.
		if (Link.getText().isBlank() || Depth.getText().isBlank())//If any of the text fields is empty,Alerts the user.
			JOptionPane.showMessageDialog(null, "Fill all fields");
		else if (!validateDepth(depth))//If depth entered isn't valid,Alerts the user.
			JOptionPane.showMessageDialog(null, "Enter numbers only in depth field");
		else if(!validateURL(link) && !validatePDF(link))//If URL entered isn't valid, Alerts the user.
			JOptionPane.showMessageDialog(null, "Enter Valid URL");
		else {
			int noValid = 0;
			int noInvalid = 0;
			int i;//i represents number of threads.
			double t1 = 1.00F;//Initialization of t1 and t2 is random so we can step in the for loop.
			double t2 = 10.00F;
			try {
				int d = Integer.parseInt(depth);
				List<Double> list = new ArrayList<Double>();
				for (i = 1; Math.abs(t2 - t1) > 0.1 && (i - 1) < 20; i++) {//Starts from i=1 because there is no meaning for 0 threads.
					long start = System.currentTimeMillis(); // Gets the time exactly before the threads starts executing.
					Link L = new Link(link, i);

					try {
						L.extractLinks(L.getLinkText(), 0, d, null);//Current depth = 0. and text is null because there is no text in the header link.

						while (((ThreadPoolExecutor) L.pool).getActiveCount() != 0) {//Makes the program wait until threads finish their tasks.

						}
						L.pool.shutdown();//Makes the threads don't accept any new tasks.
						L.pool.awaitTermination(15, TimeUnit.MINUTES);//Terminates all threads after specified time or when threads finish all tasks.

						long end = System.currentTimeMillis();//Gets time exactly when all tasks are finished.
						t2 = t1;//Saves the time for current run in t2 to be previous run time in the next run.
						t1 = (end - start) / 1000F; // Gets the time elapsed of execution for i number threads.
						if (i >= 2) {//To make the program multi-threaded, we add time elapsed for minimum 2 threads.
							list.add(t1);
							System.out.println("Time elapsed for no. of threads = " + (i) + " equals " + t1);
						}

						noValid = L.getNumberValid();
						noInvalid = L.getNumberInvalid();
						// System.out.println("Total is " + L.getTotal());
						// System.out.println("Number of valid is " + L.getNumberValid());
						// System.out.println("Number of invalid is " + L.getNumberInvalid());
					} catch (MalformedURLException e) {

					} catch (IOException e) {

					} catch (InterruptedException e) {

					} catch (RejectedExecutionException e) {

					} catch (NumberFormatException e) {

					}

				}

				int noThreads = list.indexOf(Collections.min(list)); //Gets number of threads which achieves minimum time.
				double optimalTime = list.get(noThreads);//Gets minimum time.
				OutputFrame frame = new OutputFrame(noThreads + 2, optimalTime, noValid, noInvalid, list);//Instance of output frame.
				frame.setVisible(true);
				this.dispose();//Close input frame.
				System.out.println(
						"The optimum number of threads = " + (noThreads + 2) + " with execution time = " + optimalTime);
			} catch (Exception e) {
			}
		}
	}

	public boolean validateDepth(String depth) { //Validates depth entered, checks that it is of integer type.
		int l = depth.length();
		for (int i = 0; i < l; i++) {
			if (!isDigit(depth.charAt(i)))
				return false;
		}
		return true;
	}

	public boolean validateURL(String link) {//Validates the entered URL.
		try {
			Document doc = Jsoup.connect(link).get();
			
			return true;
		} catch (HttpStatusException ex) {
			return false;

		} catch (IOException e) {

			return false;
		}catch(IllegalArgumentException e) {
			return false;
		}
	}
	public boolean validatePDF(String link) {//Returns true if the link is another type of file e.g. PDF
		try {
		
		URL u = new URL(link);
	    URLConnection x = u.openConnection();
	    x.getContentType();
	    if(x.getContentType().startsWith("application/"))
	    	return true;
	    else 
	    	return false;
		} catch(Exception e) {
			return false;
		}
	     
		
	}

	private void DepthActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DepthActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_DepthActionPerformed

	private void LinkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LinkActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_LinkActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField Depth;
	private javax.swing.JTextField Link;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	// End of variables declaration//GEN-END:variables
}
