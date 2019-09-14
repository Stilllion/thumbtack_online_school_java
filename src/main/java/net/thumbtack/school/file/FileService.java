package net.thumbtack.school.file;

import java.io.*;

import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.figures.v3.Point2D;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import com.google.gson.Gson;

public class FileService
{
	// Записывает массив байтов в двоичный файл, имя файла задается текстовой строкой.
	public static void  writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException
	{
		try(FileOutputStream fos = new FileOutputStream(fileName);){
			fos.write(array);
		} catch (FileNotFoundException e) {
            throw new IOException();
        } catch (IOException e) {
			throw new IOException();
        }
	}

	// Записывает массив байтов в двоичный файл, имя файла задается  экземпляром класса File.
	public static void  writeByteArrayToBinaryFile(File file, byte[] array) throws IOException
	{
		writeByteArrayToBinaryFile(file.getAbsolutePath(), array);
	}

	// Читает массив байтов из двоичного файла, имя файла задается текстовой строкой.
	public static byte[]  readByteArrayFromBinaryFile(String fileName) throws IOException
	{
		byte[] inArray = null;
		
		try(FileInputStream fis = new FileInputStream(fileName);)
        {
        	inArray = new byte[fis.available()];

			fis.read(inArray);

		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
		
		return inArray;
	}

	// Читает массив байтов из двоичного файла, имя файла задается экземпляром класса File.
	public static byte[]  readByteArrayFromBinaryFile(File file) throws IOException
	{
		return readByteArrayFromBinaryFile(file.getAbsolutePath());
	}

	// Записывает массив байтов в ByteArrayOutputStream, создает на основе данных в полученном  ByteArrayOutputStream
	// экземпляр ByteArrayInputStream и читает из ByteArrayInputStream байты с четными номерами.
	// Возвращает массив прочитанных байтов.
	public static byte[]  writeAndReadByteArrayUsingByteStream( byte[] array) throws IOException
	{
		byte[] retArr = null;
		
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream ())
		{
			bos.write(array);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());)
            {
				retArr = new byte[bis.available() / 2];
				
				for (int i = 0; i < retArr.length; ++i) {
					retArr[i] = (byte) bis.read();
					bis.skip(1);
				}
				
            } catch (IOException e) {
                throw new IOException();
            }

		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
           throw new IOException();
        }

		return retArr;
	}

	// Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя файла задается текстовой строкой.
	public static void  writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException
	{
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName)))
		{
			bos.write(array);

		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
           throw new IOException();
        }
	}

	// Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя файла задается экземпляром класса File.
	public static void  writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException
	{
		writeByteArrayToBinaryFileBuffered(file.getAbsolutePath(), array);
	}

	// Читает массив байтов из двоичного файла, используя буферизованный ввод, имя файла задается текстовой строкой.
	public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException
	{
		byte[] retArray = null;

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName)))
		{
			retArray = new byte[bis.available()];
			bis.read(retArray);
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }

		return retArray;
	}

	// Читает массив байтов из двоичного файла, используя буферизованный ввод, имя файла задается экземпляром класса File.
	public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException
	{
		return readByteArrayFromBinaryFileBuffered(file.getAbsolutePath());
	}

	// Записывает Rectangle в двоичный файл, имя файла задается экземпляром класса File. Поле цвета не записывать.
	public static void  writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException
	{
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));)
		{
			dos.writeInt(rect.getTopLeft().getX());
			dos.writeInt(rect.getTopLeft().getY());

			dos.writeInt(rect.getBottomRight().getX());
			dos.writeInt(rect.getBottomRight().getY());

		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			throw new IOException();
		}
	}

	// Читает данные для Rectangle из двоичного файла и создает на их основе экземпляр Rectangle, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”.
	public static Rectangle readRectangleFromBinaryFile(File file) throws ColorException, IOException
	{
		Rectangle retRect = null;
		
		try(DataInputStream dis = new DataInputStream(new FileInputStream(file));)
		{			
			retRect = new Rectangle(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt(), "RED");
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
		
		return retRect;
	}

	// Записывает массив из Rectangle в двоичный файл, имя файла задается экземпляром класса File. Поле цвета не записывать.
	public static void  writeRectangleArrayToBinaryFile(File file, Rectangle[] rects ) throws IOException
	{
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file)))
		{	
			for(int i = 0; i < rects.length; ++i){
				dos.writeInt(rects[i].getTopLeft().getX());
				dos.writeInt(rects[i].getTopLeft().getY());
				
				dos.writeInt(rects[i].getBottomRight().getX());
				dos.writeInt(rects[i].getBottomRight().getY());
			}
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			throw new IOException();
		}
	}

	// Предполагается, что данные в файл записаны в формате предыдущего упражнения.
	// Метод читает вначале данные о последнем записанном в файл Rectangle
	// и создает на их основе экземпляр Rectangle, затем читает данные следующего с конца Rectangle
	// и создает на их основе экземпляр Rectangle 
	// и т.д.  вплоть до данных для самого первого записанного в файл Rectangle.
	// Из созданных таким образом экземпляров Rectangle создается массив, который и возвращает метод.
	// Поскольку файл не содержит цвета, установить во всех Rectangle цвет “RED”.
	
	public static Rectangle[]  readRectangleArrayFromBinaryFileReverse(File file) throws ColorException, IOException
	{
		Rectangle[] rectArr = null;

		try (RandomAccessFile raf = new RandomAccessFile(file, "r"))
        {
			int offset = (int) raf.length();
			
			rectArr = new Rectangle[offset / 16];

			for (int i = 0; i < rectArr.length; ++i) {
				offset -= 16;
				
				raf.seek(offset);
				
				Rectangle tempRect = new Rectangle(raf.readInt(), raf.readInt(), raf.readInt(), raf.readInt(), "RED");
		
				rectArr[i] = tempRect;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}

		return rectArr;
	}

	// Записывает Rectangle в текстовый файл в одну строку, имя файла задается экземпляром класса File.
	// Поля в файле разделяются пробелами. Поле цвета не записывать.
	public static void  writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException
	{
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file)))
		{	
			dos.writeInt(rect.getTopLeft().getX());
			dos.writeInt(rect.getTopLeft().getY());
			
			dos.writeChar(' ');
			
			dos.writeInt(rect.getBottomRight().getX());
			dos.writeInt(rect.getBottomRight().getY());
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}
	}

	// Читает данные для Rectangle из текстового файла и создает на их основе экземпляр Rectangle,
	// имя файла задается экземпляром класса File.
	// Предполагается, что данные в файл записаны в формате предыдущего упражнения.
	// Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”
	public static Rectangle  readRectangleFromTextFileOneLine(File file) throws ColorException, IOException
	{
		 Rectangle retRect = null;

		try(DataInputStream dis = new DataInputStream(new FileInputStream(file));)
		{
			Point2D topLeft = new Point2D(dis.readInt(), dis.readInt());
			
			dis.readChar();
			
			Point2D bottomRight = new Point2D(dis.readInt(), dis.readInt());
			
			retRect = new Rectangle(topLeft, bottomRight, "RED");
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
		
		return retRect;
	}

	// Записывает Rectangle в текстовый файл, каждое число в отдельной строке,
	// имя файла задается экземпляром класса File. Поле цвета не записывать.
	public static void  writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException
	{
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file)))
		{
			dos.writeInt(rect.getTopLeft().getX());
			dos.writeChar('\n');
			dos.writeInt(rect.getTopLeft().getY());
			dos.writeChar('\n');
						
			dos.writeInt(rect.getBottomRight().getX());		
			dos.writeChar('\n');
			dos.writeInt(rect.getBottomRight().getY());
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
	}

	// Читает данные для Rectangle из текстового файла и создает на их основе экземпляр Rectangle,
	// имя файла задается экземпляром класса File.
	// Предполагается, что данные в файл записаны в формате предыдущего упражнения.
	// Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”
	public static Rectangle  readRectangleFromTextFileFourLines(File file) throws ColorException, IOException
	{
		Rectangle retRect = null;
		
		try(DataInputStream dis = new DataInputStream(new FileInputStream(file));)
		{
            int topLeftX = dis.readInt();
			dis.readChar();
	
			int topLeftY = dis.readInt();
			dis.readChar();
			
			int bottomRightX = dis.readInt();
			dis.readChar();
	
			int bottomRightY = dis.readInt();
	
			retRect = new Rectangle(topLeftX, topLeftY, bottomRightX, bottomRightY, "RED");
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
		
		return retRect;
	}

	// Записывает Trainee в текстовый файл в одну строку в кодировке UTF-8, имя файла задается экземпляром класса File.
	// Имя, фамилия и оценка в файле разделяются пробелами. 
	public static void  writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException
	{
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) 
		{
			dos.writeUTF(trainee.getFirstName());
			dos.writeChar(' ');
			
			dos.writeUTF(trainee.getLastName());
			dos.writeChar(' ');
			
			dos.writeInt(trainee.getRating());
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
           throw new IOException();
        }
	}

	// Читает данные для Trainee из текстового файла и создает на их основе экземпляр Trainee,
	// имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. 
	public static Trainee readTraineeFromTextFileOneLine(File file) throws TrainingException, IOException
	{
		Trainee retTrainee = null;
		
		try(DataInputStream dis = new DataInputStream(new FileInputStream(file));)
		{
			String fName = dis.readUTF();
			
			dis.readChar();
			
			String lName = dis.readUTF();
			
			dis.readChar();
			
			int rating = dis.readInt();
			
			retTrainee = new Trainee(fName, lName, rating);
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }

		return retTrainee;	
	}

	// Записывает Trainee в текстовый файл в кодировке UTF-8, каждое поле в отдельной строке,
	// имя файла задается экземпляром класса File. 
	public static void  writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException
	{
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            bw.write(trainee.getFirstName());
            bw.newLine();
			
            bw.write(trainee.getLastName());
            bw.newLine();
			
			bw.write(trainee.getRating());
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
			throw new IOException();
        }
	}

	// Читает данные для Trainee из текстового файла и создает на их основе экземпляр Trainee,
	// имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. 
	public static Trainee  readTraineeFromTextFileThreeLines(File file) throws TrainingException, IOException
	{
		Trainee retTrainee = null;
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))
		{		
            String fName = br.readLine();
            String lName = br.readLine();
            int rating = br.read();
			
			retTrainee = new Trainee(fName, lName, rating);
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
			throw new IOException();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
           throw new IOException();
        }
		
		return retTrainee;	
    }

	// Сериализует Trainee в двоичный файл, имя файла задается экземпляром класса File. 
	public static void  serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
		{
			oos.writeObject(trainee);
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			throw new IOException();
		}
	}

	// Десериализует Trainee из двоичного файла, имя файла задается экземпляром класса File.
	// Предполагается, что данные в файл записаны в формате предыдущего упражнения. 
	public static Trainee  deserializeTraineeFromBinaryFile(File file) throws TrainingException, IOException
	{
		Trainee trainee = null;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
		{
			trainee = (Trainee) ois.readObject();
			
		} catch (IOException e) {
			throw new IOException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return trainee;
	}
	 
	// Сериализует Trainee в формате Json в текстовую строку. 
	public static String  serializeTraineeToJsonString(Trainee trainee)
	{
		Gson gson = new Gson();
        String gsonText = gson.toJson(trainee);
		
        return gsonText;  
	}

	// Десериализует Trainee из текстовой строки с Json-представлением Trainee.
	public static Trainee  deserializeTraineeFromJsonString(String json) throws TrainingException
	{
		Gson gson = new Gson();
		Trainee retTrainee = null;
		return retTrainee = gson.fromJson(json, Trainee.class);
	}

	// Сериализует Trainee в формате Json в файл, имя файла задается экземпляром класса File. 
	public static void  serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException
	{
        Gson gson = new Gson();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
            gson.toJson(trainee, bw);
			
        } catch (IOException e) {
            throw new IOException();
        }        
	}

	// Десериализует Trainee из файла с Json-представлением Trainee, имя файла задается экземпляром класса File.
	public static Trainee  deserializeTraineeFromJsonFile(File file) throws TrainingException, IOException
	{
		Gson gson = new Gson();
		Trainee retTrainee = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
            retTrainee = gson.fromJson(br, Trainee.class);
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException();
        }
		
		return retTrainee;
	}

}