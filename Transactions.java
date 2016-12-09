import java.util.*;
import java.io.*;

public class Transactions{
	/**
	*	meliputi fungsi
	*	- pembelian
	*	- inquiry transaksi pembelian
	*/
	
	// property getter setter
	private String inventoryFile;
	public void setInventoryFile(String s){
		this.inventoryFile = s;
	}
	public String getInventoryFile(){
		return this.inventoryFile;
	}
	
	private String transactionsFile;
	public void setTransactionsFile(String s){
		this.transactionsFile = s;
	}
	public String getTransactionsFile(){
		return this.transactionsFile;
	}
	
	
	public void pembelian(){
		try{
			String namaFileTransaksi = getTransactionsFile();
			String namaFileObat = getInventoryFile();
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

			// jumlah obat yang ingin dibeli
			System.out.println("Masukkan jumlah jenis obat yang mau dibeli:");
			int jumlahDibeli = scanner.nextInt();
			scanner.nextLine();
		
		
			// ambil data yang mau dibeli, cek qty nya
			// loop berdasarkan jumlahDibeli
			String[] obat = new String[jumlahDibeli];
			int[] qty = new int[jumlahDibeli];
			int[] qtyBeli = new int[jumlahDibeli];
			for(int dibeli=0; dibeli<jumlahDibeli; dibeli++){
				System.out.println("Masukkan nomor obat yang akan di beli: ");
				int pilihan = scanner.nextInt();
				scanner.nextLine();
				obat[dibeli] = String.valueOf(listObat.get(pilihan -1));
				String[] obats = obat[dibeli].split(",");

				//int qty =  Integer.parseInt(obats[1]);
				qty[dibeli] =  Integer.parseInt(obats[1]);
				System.out.println("Masukkan jumlah obat yang ingin dibeli : ");
				//int qtyBeli = scanner.nextInt();
				qtyBeli[dibeli] = scanner.nextInt();
				scanner.nextLine();
				if(qtyBeli[dibeli] > qty[dibeli]){
					System.out.println("Qty pembelian melebihi batas stok. Aborting. \n \n");
					return;
				} else{
					qty[dibeli] -= qtyBeli[dibeli];
				}
			}

			// replace line data lama nya dengan data baru
			// loop berdasarkan jumlahDibeli
			//String[jumlahDibeli] oldData = obat;
			//String[jumlahDibeli] newData = obats[0] + "," + qty + "," + obats[2];
			String[] oldData = obat;
			String[] newData = new String[jumlahDibeli];
			for(int x=0; x<jumlahDibeli; x++){
				newData[x] = oldData[x].split(",")[0] + "," + qty[x] + "," + oldData[x].split(",")[2];
			}
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
				// loop data berdasar jumlahDibeli
				// untuk replace semua data yang dibeli pada txt inventory
				for(int ganti=0; ganti<jumlahDibeli; ganti++){
					totalStr = totalStr.replaceAll(oldData[ganti], newData[ganti]);
				}
				FileWriter fw = new FileWriter(fileObat);
				fw.write(totalStr);
				fw.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Stok inventory berhasil diupdate");

			// namacustomer, namaobat, qty, total
			//String transactionString = namaCustomer + "," + obats[0] + "," + qty + "," + Integer.parseInt(obats[2])*qty; 
			// masukin data pembelian ke transactions.txt
			FileWriter writer = new FileWriter(new File(namaFileTransaksi), true);
			for(int tsa=0; tsa<jumlahDibeli; tsa++){
				writer.write(namaCustomer + "," + newData[tsa].split(",")[0] + "," + qtyBeli[tsa] + "," + Integer.parseInt(newData[tsa].split(",")[2]) * qtyBeli[tsa]);
				writer.write("\n");
			}
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
	
	public void inquiryHistoryTransaksi(){
		try{
			Scanner dataTransaksi = new Scanner(new File(getTransactionsFile()));
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
}