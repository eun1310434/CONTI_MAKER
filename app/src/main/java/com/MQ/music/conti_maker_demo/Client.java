package com.MQ.music.conti_maker_demo;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public String Ad_date = "";
	public String Ad_title = "";
	public String Ad_context = "";
	public String Ad_button = "";

	public void MustStart() {

		// �ȵ���̵� 4.0.1 ������ �� ���ʹ� ������ ���� �ڵ带 �� ����ؾ� �ȴ�.
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}

	// t0
	public void ClientAccessHistory(String SendMsg) {
		// ������� ��� ����� �����մϴ�.

		MustStart();

		try {

			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// Type ����
			dos.writeUTF("t0");
			dos.flush();

			// PhoneID ����
			dos.writeUTF(SendMsg);
			dos.flush();

			// ���� ����
			din.close();
			in.close();

			dos.close();
			out.close();
			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean Advertise() {
		/*
		 * ���� Ȯ���޴��� Ȯ���մϴ�.
		 */

		boolean Result = false;

		MustStart();
		try {
			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// Type ����
			dos.writeUTF("t4");
			dos.flush();

			// ���� Ȯ��
			/*
			 * Y -> ���� �ִ� ��� N -> ���� ���� ���
			 */
			String Ad_check = din.readUTF();

			if (Ad_check.equals("Y")) {

				Ad_date = din.readUTF();
				Ad_title = din.readUTF();
				Ad_context = din.readUTF();
				Ad_button = din.readUTF();

				Result = true;
			} else if (Ad_check.equals("N")) {

				Result = false;
			}

			// ���� ����
			din.close();
			in.close();

			dos.close();
			out.close();
			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Result;
	}

	public String NewRegistration(String My_s_id) {

		MustStart();
		String Msg;

		try {
			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// Type ����
			dos.writeUTF("NewRegistration");
			dos.flush();

			// My_s_id ����
			dos.writeUTF(My_s_id);
			dos.flush();

			// ��� �Է�
			String readMsg = din.readUTF();

			// ���� ����
			din.close();
			in.close();

			dos.close();
			out.close();
			socket.close();

			return readMsg;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Msg = "ServerCheck";
		return Msg;
	}

	public void SendMyId(String s_id, String msg) {
		/*
		 * ������ ���ϴ� ��� �ڽ��� PhoneID�� �����մϴ�.
		 */

		MustStart();
		try {
			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			// Type ����
			dos.writeUTF("SendMyId");
			dos.flush();

			// s_id ����
			dos.writeUTF(s_id);
			dos.flush();

			dos.writeUTF(msg);
			dos.flush();

			dos.close();
			out.close();
			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean SendRequest(String Myid, String email, String request,
			String fight) {
		/*
		 * ��û �Ǻ��� �ִ°�� ������ �����մϴ�.
		 */

		MustStart();
		String Msg;
		try {
			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// Type ����
			dos.writeUTF("t3");
			dos.flush();

			// PhoneID ����
			dos.writeUTF(Myid);
			dos.flush();

			// ����ó ����
			dos.writeUTF(email);
			dos.flush();

			// ��û�� ����
			dos.writeUTF(request);
			dos.flush();

			// ��Ÿ �䱸���� ����
			dos.writeUTF(fight);
			dos.flush();

			// ��� �Է�
			Msg = din.readUTF();

			din.close();
			in.close();

			dos.close();
			out.close();
			socket.close();

			if (Msg.equals("Y")) {

				return true;
			} else {

				return false;
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String IdSearch(String mb_id) {
		// ������� ��� ����� �����մϴ�.

		MustStart();
		String Msg = "ServerCheck";
		try {

			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// Type ����
			dos.writeUTF("IdSearch");
			dos.flush();

			// PhoneID ����
			dos.writeUTF(mb_id);
			dos.flush();

			// ��� �ޱ�
			Msg = din.readUTF();

			// ���� ����
			din.close();
			in.close();

			dos.close();
			out.close();
			socket.close();

			return Msg;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Msg;
	}

	public String DownFriendPic(String mb_id) {

		MustStart();
		String Msg = "ServerCheck";

		File f1 = new File(Environment.getExternalStorageDirectory()
				+ "/contimaker");
		if (f1.exists() == false) {
			f1.mkdir();
		}

		try {

			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			// Type ����
			dos.writeUTF("DownFriendPic");
			dos.flush();

			// m_id ����
			dos.writeUTF(mb_id);
			dos.flush();

			// ��� �ޱ�
			Msg = din.readUTF();// Y: ���� ����, N: ��������

			if (Msg.equals("Y")) {

				int total = 0;

				FileOutputStream fos = new FileOutputStream(new File(
						Environment.getExternalStorageDirectory()
								+ "/contimaker/" + mb_id + ".png"));
				byte[] buf = new byte[2048];
				while (true) {
					int count = in.read(buf);
					total = total + count;
					Log.e("", "read bytes : " + Integer.toString(total));
					if (count == -1) {
						break;
					}
					fos.write(buf, 0, count);
					fos.flush();
				}
				fos.close();

			} else if (Msg.equals("N")) {

			}

			din.close();
			in.close();

			dos.close();
			out.close();

			socket.close();

			return Msg;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Msg;
	}

	public String DownMusicScore(String m_id) {

		MustStart();
		String Msg = "ServerCheck";

		File f1 = new File(Environment.getExternalStorageDirectory()
				+ "/contimaker");
		if (f1.exists() == false) {
			f1.mkdir();
		}

		// ���� ����
		File f2 = new File(Environment.getExternalStorageDirectory()
				+ "/contimaker/score");
		if (f2.exists() == false) {
			f2.mkdir();
		}

		try {

			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			// Type ����
			dos.writeUTF("DownMusicScore");
			dos.flush();

			// m_id ����
			dos.writeUTF(m_id);
			dos.flush();
			int total = 0;

			FileOutputStream fos = new FileOutputStream(new File(
					Environment.getExternalStorageDirectory()
							+ "/contimaker/score/" + m_id + ".png"));
			byte[] buf = new byte[2048];
			while (true) {
				int count = in.read(buf);
				total = total + count;
				Log.e("", "read bytes : " + Integer.toString(total));
				if (count == -1) {
					break;
				}
				fos.write(buf, 0, count);
				fos.flush();
			}
			fos.close();

			din.close();
			in.close();

			dos.close();
			out.close();

			socket.close();

			return Msg;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Msg;
	}

	public String SendMyPicture(String s_id) {

		MustStart();
		String Msg = "ServerCheck";

		File f1 = new File(Environment.getExternalStorageDirectory()
				+ "/contimaker");
		if (f1.exists() == false) {
			f1.mkdir();
		}

		// ���� ����
		File f2 = new File(Environment.getExternalStorageDirectory()
				+ "/contimaker/score");
		if (f2.exists() == false) {
			f2.mkdir();
		}

		try {

			// ����
			Socket socket = new Socket("180.231.204.46", 5553);

			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			// Type ����
			dos.writeUTF("SendMyPicture");
			dos.flush();

			String filePath = Environment.getExternalStorageDirectory()
					+ "/contimaker/" + s_id + ".png";
			File f = new File(filePath);

			if (f.exists() == false) {

				dos.writeUTF("NoImage");
				dos.flush();

				// m_id ����
				dos.writeUTF(s_id);
				dos.flush();

			} else {

				// m_id ����
				dos.writeUTF(s_id);
				dos.flush();

				DataInputStream down_dis = new DataInputStream(
						new FileInputStream(new File(
								Environment.getExternalStorageDirectory()
										+ "/contimaker/" + s_id + ".png")));
				byte[] buf = new byte[1024];
				while (true) {
					int count = down_dis.read(buf);
					if (count == -1) {
						break;
					}
					dos.write(buf, 0, count);
					dos.flush();
				}

				down_dis.close();
			}

			dos.close();
			out.close();

			socket.close();

			return Msg;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Msg;

	}

}
