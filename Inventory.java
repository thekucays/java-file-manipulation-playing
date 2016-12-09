import java.util.*;
import java.io.*;

public class Inventory{
	/**
	*	meliputi fungsi
	*	- input obat baru
	*	- update data obat
	*	- inquiry data obat
	*/
	
	// property getter setter
	private String inventoryFile;
	public void setInventoryFile(String s){
		this.inventoryFile = s;
	}
	public String getInventoryFile(){
		return this.inventoryFile;
	}
	
	
	public void inputObatBaru(){
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
			FileWriter fw = new FileWriter(new File(getInventoryFile()).getName(), true);  // set true supaya nambahin isinya, bukan bikin baru
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
	
	public void updateDataObat(){
		try{
			String namaFile = getInventoryFile();
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
	
	public void inquiryDataObat(){
		try{
			Scanner dataObat = new Scanner(new File(getInventoryFile()));
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
}