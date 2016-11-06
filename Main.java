import java.util.*;
import java.io.*;


public class Main{
	public static void inquiryDataObat(){
		try{
			Scanner dataObat = new Scanner(new File("inventory.txt"));
			dataObat.nextLine();
			System.out.println("Berikut list data obat yang tersedia");

			while(dataObat.hasNext()){
				String baris = dataObat.nextLine();
				String[] data = baris.split(",");
					System.out.println(
						"Nama Barang: " + data[0]
						 + ", Qty: " + data[1]
						+ ", Harga: " + data[2]
					);
			}
			dataObat.close();
		} catch(Exception e){
			System.out.println("file data obat tidak ketemu..periksa kembali parameter yang dimasukkan");
		}
	}

	public static void updateDataObat(){
		try{
			String namaFile = "inventory.txt";
			Scanner dataObat = new Scanner(new File(namaFile));
			dataObat.nextLine();
			ArrayList listObat = new ArrayList();
			int listObatSize;
			int numbering = 1;
			System.out.println("Berikut list data obat yang tersedia");		

			// simpen datanya dulu ke dalem ArrayList nya
			while(dataObat.hasNext()){
				String baris = dataObat.nextLine();
				listObat.add(baris);

				String[] data = baris.split(",");
				System.out.println(
					numbering 
					+ ". "
					+ data[0]
					+ ", stok: " + data[1]
					+ ", harga: " + data[2]
				);
				numbering++;
			}
			listObatSize = listObat.size();
			dataObat.close();

			System.out.println("masukkan nomor data yang akan dirubah");
			Scanner inputan = new Scanner(System.in);
			int tempInput = inputan.nextInt();
			inputan.nextLine();

			// masukin data obat yang baru
			String oldDatas = String.valueOf(listObat.get(tempInput-1));
			String[] oldData = oldDatas.split(",");
			
			System.out.println("Nama Lama: " + oldData[0] + "\n Nama Baru: ");
			String namaBaru = inputan.nextLine();
			
			System.out.println("Qty Lama: " + oldData[1] + "\n Qty Baru: ");
			int tempQtyBaru = inputan.nextInt();
			inputan.nextLine();
			String qtyBaru = String.valueOf(tempQtyBaru);

			System.out.println("Harga Lama: " + oldData[2] + "\n Harga Baru: ");
			int tempHargaBaru = inputan.nextInt();
			inputan.nextLine();
			String hargaBaru = String.valueOf(tempHargaBaru);

			String dataBaru = namaBaru + "," + qtyBaru + "," + hargaBaru;
			listObat.set(tempInput - 1, dataBaru);

			// input data baru ke dalam file nya sesuai dengan ArrayList nya
			// .. di set false, jadi file nya di overwrite
			FileWriter fw = new FileWriter(new File(namaFile), false);
			fw.write("nama_barang,qty,harga \n");
			for(int i=0; i<listObatSize; i++){
				fw.write(String.valueOf(listObat.get(i)));
				fw.write("\n");
			}
			fw.close();
			System.out.println("Data Obat berhasil di update");

		} catch(FileNotFoundException e){
			System.out.println("file data obat tidak ketemu..periksa kembali parameter yang dimasukkan");
		} catch(NumberFormatException nfe){
			System.out.println("Input harus berupa angka. Periksa kembali inputan Anda.");
		} catch(IOException ie){
			System.out.println("Terjadi kesalahan penulisan data. Periksa apakah file ada dan penamaan nya benar");
		}
	}	

	public static void pembelian(){
		try{
			String namaFileTransaksi = "transactions.txt";
			String namaFileObat = "inventory.txt";
			String namaCustomer = "";
			Scanner inputan = new Scanner(System.in);
			ArrayList listObat = new ArrayList();
			Scanner scanner = new Scanner(System.in);

			// masukin nama customer..bisa di bikin default
			System.out.println("Nama Customer (enter for skip) :  ");
			namaCustomer = inputan.nextLine();
			if(namaCustomer.trim().length() == 0){
				namaCustomer = "default";
			}

			// print data obat
			Scanner dataObat = new Scanner(new File(namaFileObat));
			dataObat.nextLine();
			int listObatSize;
			int numbering = 1;
			System.out.println("Berikut list data obat yang tersedia");		
			while(dataObat.hasNext()){
				String baris = dataObat.nextLine();
				listObat.add(baris);
				String[] data = baris.split(",");
				System.out.println(
					numbering 
					+ ". "
					+ data[0]
					+ ", stok: " + data[1]
					+ ", harga: " + data[2]
				);
				numbering++;
			}
			listObatSize = listObat.size();
			dataObat.close();

			// ambil data yang mau dibeli, cek qty nya
			System.out.println("Masukkan nomor obat yang akan di beli: ");
			int pilihan = scanner.nextInt();
			scanner.nextLine();
			String obat = String.valueOf(listObat.get(pilihan -1));
			String[] obats = obat.split(",");

			int qty =  Integer.parseInt(obats[1]);
			System.out.println("Masukkan jumlah obat yang ingin dibeli : ");
			int qtyBeli = scanner.nextInt();
			scanner.nextLine();
			if(qtyBeli > qty){
				System.out.println("Qty pembelian melebihi batas stok. Aborting. \n \n");
				return;
			} else{
				qty -= qtyBeli;
			}

			// replace line data lama nya dengan data baru
			String oldData = obat;
			String newData = obats[0] + "," + qty + "," + obats[2];
			File fileObat = new File(namaFileObat);
			FileReader fr = new FileReader(fileObat);
			try{
				String s;
				String totalStr = "";
				BufferedReader br = new BufferedReader(fr);
				while((s=br.readLine()) != null){
					totalStr += s;
					
					/**
						fix bug, supaya tetep nga baca newline (enter) nya
						originally taken from http://stackoverflow.com/questions/23466179/java-replace-specific-string-in-textfile
					*/
					totalStr += "\n";
				}
				totalStr = totalStr.replaceAll(oldData, newData);

				FileWriter fw = new FileWriter(fileObat);
				fw.write(totalStr);
				fw.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Stok inventory berhasil diupdate");

			// namacustomer, namaobat, qty, total
			String transactionString = namaCustomer + "," + obats[0] + "," + qty + "," + Integer.parseInt(obats[2])*qty; 

			// masukin data pembelian ke transactions.txt
			FileWriter writer = new FileWriter(new File(namaFileTransaksi), true);
			writer.write(transactionString);
			writer.write("\n");
			writer.close();
			System.out.println("Data transaksi berhasil dimasukkan");

		} catch(NumberFormatException nfe){
			System.out.println("nfe");
		} catch(FileNotFoundException fne){
			System.out.println("fne");
		} catch(IOException ie){
			System.out.println("ioe");
		}
	}

	public static void inputObatBaru(){
		String nama, qty, harga;
		Scanner scanner = new Scanner(System.in);

		// input data baru
		System.out.println("INPUT OBAT BARU");
		System.out.println("Masukkan nama obat");
		nama = scanner.nextLine();

		int temp;
		try{
			System.out.println("Masukkan qty obat");
			temp = scanner.nextInt();
			scanner.nextLine();
			qty = String.valueOf(temp);

			System.out.println("Masukkan harga obat");
			temp = scanner.nextInt();
			scanner.nextLine();
			harga = String.valueOf(temp);

			// masukkan data baru ke dalam file txt
			String newData = nama + "," + qty + "," + harga;
			FileWriter fw = new FileWriter(new File("inventory.txt").getName(), true);  // set true supaya nambahin isinya, bukan bikin baru
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(newData);
			bw.write("\n");
			bw.close();

			System.out.println("Data obat baru berhasil dimasukkan");

		} catch(NumberFormatException nfe){
			System.out.println("Data yang dimasukkan harus berupa angka. Periksa kembali inputan Anda. \n");
		} catch(IOException ie){
			System.out.println("Terjadi kesalahan penulisan data. Periksa apakah file ada dan penamaan nya benar");
		}
	}

	public static void inquiryHistoryTransaksi(){
		try{
			Scanner dataTransaksi = new Scanner(new File("transactions.txt"));
			dataTransaksi.nextLine();
			System.out.println("Berikut data transaksi yang tersedia");

			while(dataTransaksi.hasNext()){
				String baris = dataTransaksi.nextLine();
				String[] data = baris.split(",");
					System.out.println(
						"Nama Pelanggan: " + data[0]
						+ ", Nama Obat: " + data[1]
						 + ", Qty: " + data[2]
						+ ", Total: " + data[3]
					);
			}
			dataTransaksi.close();
		} catch(Exception e){
			System.out.println("file data obat tidak ketemu..periksa kembali parameter yang dimasukkan");
		}
	}

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

			switch(pilihan){
				case 1:
					inputObatBaru();
					break;
				case 2:
					updateDataObat();
					break;
				case 3:
					pembelian();
					break;
				case 4:
					inquiryDataObat();
					break;
				case 5:
					inquiryHistoryTransaksi();
					break;
				case 6:
					System.out.println("bye.");
					System.exit(4);
					break;
				default:
					System.out.println("Menu tidak tersedia.. silahkan periksa kembali pilihan Anda. \n");
					break;
			}
		} while (pilihan <= 6);
	}
	
}