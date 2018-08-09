/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabackend_lab06;


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaBackend_Lab06 {

	private static int a=-1,b=-1,c=-1;
	static AtomicInteger laCo = new AtomicInteger(0);// 0 là t1 hoặc t2 đang chạy , 
	//1 là t3 chạy
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (laCo) {
					for (int i = 0; i < 100;) {
						try {
							if (laCo.get()==0 && a <0 ) {
								a = new Random().nextInt(100);
								i++;
								System.out.println("gia tri a[" + i + "] =" +a);
								Thread.sleep(10);
								if (b >=0) {
									laCo.set(1);
								}
								laCo.notifyAll();
							} else {
								laCo.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (laCo) {
					for (int i = 0; i < 100;) {
						try {
							if (laCo.get()==0 && b <0) {
								b = new Random().nextInt(25);
								i++;
								System.out.println("gia tri b[" + i + "] =" +b);
								Thread.sleep(10);
								if (a >=0) {
									laCo.set(1);
								}
								laCo.notifyAll();
							} else {
								laCo.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (laCo) {
					for (int i = 0; i < 100;) {
						try {
							if (laCo.get()==1) {
								c = a + b;
								i++;
								System.out.println("c : " +c);
								System.out.println( " i = " +i);
								Thread.sleep(10);
								laCo.set(0);
								a =b=-1;
								laCo.notifyAll();
							} else {
								laCo.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		t2.start();
		t1.start();
		t3.start();
	}
}
