import java.util.*;
import java.io.*;


public class Main{
	public static void tampilMenu(){
		// method sederhana cuma untuk menampilkan menu utama
		System.out.println("Apotik");
		System.out.println("1. Input obat baru");
		System.out.println("2. Update data obat");
		System.out.println("3. Pembelian");
		System.out.println("4. Inquiry data obat");
		System.out.println("5. Inquiry transaksi pembelian");
		System.out.println("6. Exit progam");
		System.out.println("Masukkan pilihan:  ");
	}

	public static void main(String[] args){
		int pilihan;

		do{
			/**
				note: 
				ga perlu close Scanner yang System.in di method manapun karena akan berlaku ke semua
				http://stackoverflow.com/questions/13042008/java-util-nosuchelementexception-scanner-reading-user-input
			*/
			Scanner scanner = new Scanner(System.in);
			tampilMenu();
			pilihan = scanner.nextInt();
			
			// objek class Inventory.java dan Transactions.java
			Inventory inventory = new Inventory();
			Transactions transactions = new Transactions();

			switch(pilihan){
				case 1:
					inventory.setInventoryFile("inventory.txt");
					inventory.inputObatBaru();
					break;
				case 2:
					inventory.setInventoryFile("inventory.txt");
					inventory.updateDataObat();
					break;
				case 3:
					transactions.setInventoryFile("inventory.txt");
					transactions.setTransactionsFile("transactions.txt");
					transactions.pembelian();
					break;
				case 4:
					inventory.setInventoryFile("inventory.txt");
					inventory.inquiryDataObat();
					break;
				case 5:
					transactions.setTransactionsFile("transactions.txt");
					transactions.inquiryHistoryTransaksi();
					break;
				case 6:
					System.out.println("bye.");
					System.exit(4);
					break;
				default:
					System.out.println("Menu tidak tersedia.. silahkan periksa kembali pilihan Anda. \n");
					tampilMenu();
					//pilihan = scanner.nextInt();
					break;
			}
		//} while (pilihan <= 6);
		} while(true);
	}
	
}