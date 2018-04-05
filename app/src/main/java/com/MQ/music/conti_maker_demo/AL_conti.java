package com.MQ.music.conti_maker_demo;

public class AL_conti {

	int type;
	String c_name;
	String c_mdate;
	String c_writer;
	String c_subject;
	String c_new;
	
	AL_conti (int type ,String c_name ,String c_mdate ,String c_writer,String c_subject ,String c_new ){
		
		this.type = type;
		this.c_name = c_name;
		this.c_mdate = c_mdate;
		this.c_writer = c_writer;
		this.c_subject = c_subject;
		this.c_new = c_new;
	}
}