package com.MQ.music.conti_maker_demo;

public class DB_query {
	
	
	
	//=============================================================================================================================================================
	/*
	 * CREATE TABLE �Դϴ�.
	 */
	
	public String Create_MUSIC="CREATE  TABLE IF NOT EXISTS  MUSIC ( " +
														"m_id 		INTEGER 	NOT NULL ,  " +
														"m_name 	TEXT 		NOT NULL ,  " +
														"m_words 	TEXT 		NOT NULL ,  " +
														"m_code 	TEXT 		NOT NULL ,	" +
														"m_img 		INTEGER 	NOT NULL , " +
														"m_bag 		INTEGER 	 , " +
														"PRIMARY KEY (m_id)" +
														");";
	
	
	public String Create_MUSIC_SUB="CREATE TABLE IF NOT EXISTS MUSIC_SUB ( " +
														"ms_id 		INTEGER 		NOT NULL ," +
														"ms_num		INTEGER 		NOT NULL ," +
														"ms_img		INTEGER 		NOT NULL ," +
														"PRIMARY KEY (ms_id, ms_num)," +
														"FOREIGN KEY (ms_id) REFERENCES MUSIC (m_id)" +
														");";
	
	
	public String Create_CONTINUITY="CREATE  TABLE IF NOT EXISTS CONTINUITY (  " +
																				"c_name 		TEXT 		NOT NULL ," +
																				"c_mdate 		INTEGER 	NOT NULL ," +
																				"c_writer 		TEXT 		NOT NULL ," +
																				"c_subject 		TEXT 		NOT NULL ," +
																				"c_new 			TEXT 		NOT NULL ," +
																				"PRIMARY KEY (c_name)" +
																			");";
	
	public String Create_CONTI_MUSIC="CREATE  TABLE IF NOT EXISTS CONTI_MUSIC(	" +
																				"cm_c_name 		TEXT 		NOT NULL ," +
																				"cm_num 		INTEGER 	NOT NULL ," +
																				"cm_m_id 		INTEGER 	NOT NULL ," +
																				"PRIMARY KEY (cm_m_id, cm_c_name, cm_num) ," +
																				"FOREIGN KEY (cm_c_name ) REFERENCES CONTINUITY (c_name)," +
																				"FOREIGN KEY (cm_m_id ) REFERENCES MUSIC (m_id)" +
																				");";
	
	public String Create_MEMBERS="CREATE TABLE IF NOT EXISTS MEMBERS ( 	" +
																		"mb_id						TEXT 			NOT NULL ," +
																		"mb_name					TEXT 			NOT NULL ," +
																		"mb_registrationId			TEXT 			NOT NULL ," +
																		"mb_etc						TEXT 			," +
																		"PRIMARY KEY (mb_id)" +
																		");";
	
	
	public String Create_CONTI_MEMBERS ="CREATE  TABLE IF NOT EXISTS CONTI_MEMBERS (  " +
																					"cmb_c_name 		TEXT 		NOT NULL ," +
																					"cmb_mb_id 			TEXT 		NOT NULL ," +
																					"PRIMARY KEY (cmb_c_name,cmb_mb_id), " +
																					"FOREIGN KEY (cmb_c_name) REFERENCES CONTI (c_name)," +
																					"FOREIGN KEY (cmb_mb_id) REFERENCES MEMBERS (mb_id)" +
																					");";

	
	public String Create_SYSTEM="CREATE TABLE IF NOT EXISTS SYSTEM ( " +
																		"s_id 				TEXT 		NOT NULL ," +
																		"s_registrationId	TEXT 		NOT NULL," +
																		"s_name				TEXT 		NOT NULL," +
																		"s_etc				TEXT 		," +
																		"s_history			TEXT 		," +
																		"s_ad				TEXT 		," +
																		"s_version			TEXT 		," +
																		"PRIMARY KEY (s_id)" +
																	");";
	

	//=============================================================================================================================================================
	/*
	 * DROP TABLE �Դϴ�.
	 */
	public String Drop_MUSIC="DROP TABLE IF EXISTS MUSIC ;";
	public String Drop_MUSIC_SUB="DROP TABLE IF EXISTS MUSIC_SUB ;";
	public String Drop_CONTINUITY="DROP TABLE IF EXISTS CONTINUITY ;";
	public String Drop_CONTI_MUSIC="DROP TABLE IF EXISTS CONTI_MUSIC ;";
	public String Drop_MEMBERS="DROP TABLE IF EXISTS MEMBERS ;";
	public String Drop_CONTI_MEMBERS="DROP TABLE IF EXISTS CONTI_MEMBERS ;";
	public String Drop_SYSTEM="DROP TABLE IF EXISTS SYSTEM ;";
	
	

	//=============================================================================================================================================================
	/*
	 * INSERT TABLE �Դϴ�.
	 */
    public String Insert_MUSIC(int m_id, String m_name, String m_words, String  m_code, int m_img ,int m_bag){
    	String insert = "INSERT INTO	MUSIC	VALUES(	"+m_id+"	,	'"+m_name+"'	,	'"+m_words+"'	,	'"+m_code+"',	"+m_img+",	"+m_bag+"	);";
    	return insert; 	
    }
    public String Insert_MUSIC_SUB(int ms_id, int ms_num,int ms_img){
    	String insert = "INSERT INTO	MUSIC_SUB	VALUES(	"+ms_id+"	,	"+ms_num+"	,	"+ms_img+");";
    	return insert; 	
    }
    public String Insert_CONTINUITY(  String c_name,int c_mdate, String c_writer, String c_subject, String c_new){
    	String insert = "INSERT INTO	CONTINUITY	VALUES(	 '"+c_name+"'	,"+c_mdate+",	'"+c_writer+"',	'"+c_subject+"','"+c_new+"'	);";
    	return insert; 	
    }
    public String Insert_CONTI_MUSIC( String cm_c_name, int cm_num , int  cm_m_id ){
    	String insert = "INSERT INTO	CONTI_MUSIC	VALUES(		'"+cm_c_name+"',	"+cm_num+"	,"+cm_m_id+"	);";
    	return insert; 	
    }
    public String Insert_MEMBERS( String mb_id, String mb_name, String mb_registrationId , String mb_etc){
    	String insert = "INSERT INTO	MEMBERS	VALUES(		'"+mb_id+"',	'"+mb_name+"'	, '"+mb_registrationId+"'	, '"+mb_etc+"'	);";
    	return insert; 	
    }
    public String Insert_CONTI_MEMBERS( String cmb_c_name, String cmb_mb_id){
    	String insert = "INSERT INTO	CONTI_MEMBERS	VALUES(		'"+cmb_c_name+"',	'"+cmb_mb_id+"'	);";
    	return insert; 	
    }
    public String Insert_SYSTEM(String s_id, String s_registrationId ,String s_name,String s_etc,String s_history,String s_ad  ,String s_version){
    	String insert = "INSERT INTO	SYSTEM	VALUES(	'"+s_id+"'	,'"+s_registrationId+"','"+s_name+"','"+s_etc+"','"+s_history+"','"+s_ad+"','"+s_version+"');";
    	return insert; 	
    }
    

	//=============================================================================================================================================================
	/*
	 * DELETE TABLE �Դϴ�.
	 */
    
    public String Delete_MEMBERS(String mb_id){
    	String insert = "DELETE FROM 	MEMBERS		WHERE	 mb_id =  '"+mb_id+"';";
    	return insert; 	
    }
    public String Delete_CONTI_MEMBERS( String cmb_c_name, String cmb_mb_id ){
    	String insert = "DELETE FROM 	CONTI_MEMBERS		WHERE	 cmb_c_name =  '"+cmb_c_name+"' AND cmb_mb_id = '"+cmb_mb_id+"';";
    	return insert; 	
    }
    public String Delete_CONTI_MEMBERS_cmb_mb_id(String cmb_mb_id){
    	String insert = "DELETE FROM 	CONTI_MEMBERS		WHERE	 cmb_mb_id =  '"+cmb_mb_id+"';";
    	return insert; 	
    }
    public String Delete_CONTI_MEMBERS_cmb_c_name(String cmb_c_name){
    	String insert = "DELETE FROM 	CONTI_MEMBERS		WHERE	 cmb_c_name =  '"+cmb_c_name+"';";
    	return insert; 	
    }
    public String Delete_CONTINUITY( String c_name){
    	String insert = "DELETE FROM 	CONTINUITY	WHERE	c_name =  '"+c_name+"';";
    	return insert; 	
    }
    public String Delete_CONTI_MUSIC(String cm_c_name){
    	String insert = "DELETE FROM 	CONTI_MUSIC	WHERE	 cm_c_name =  '"+cm_c_name+"';";
    	return insert; 	
    }
    public String Delete_SYSTEM(String s_id){
    	String insert = "DELETE FROM 	SYSTEM	WHERE	 s_id =  '"+s_id+"';";
    	return insert; 	
    }
    
    

	//=============================================================================================================================================================
	/*
	 * UPDATE TABLE �Դϴ�.
	 */
    public String Update_MEMBERS( String mb_id, String mb_name, String mb_registrationId , String mb_etc){
    	
    	String insert = "UPDATE MEMBERS SET mb_name = '"+mb_name+"' , mb_registrationId = '"+mb_registrationId+"'  , mb_etc = '"+mb_etc+"'  WHERE mb_id = '"+mb_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_version(String s_id,String s_version){
    	String insert = "UPDATE SYSTEM SET s_version = '"+s_version+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_registrationId(String s_id,String s_registrationId){
    	String insert = "UPDATE SYSTEM SET s_registrationId = '"+s_registrationId+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_ad(String s_id,String s_ad){
    	String insert = "UPDATE SYSTEM SET s_ad = '"+s_ad+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_history(String s_id,String s_history){
    	String insert = "UPDATE SYSTEM SET s_history = '"+s_history+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_name(String s_id,String s_name){
    	String insert = "UPDATE SYSTEM SET s_name = '"+s_name+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_SYSTEM_etc(String s_id,String s_etc){
    	String insert = "UPDATE SYSTEM SET s_etc = '"+s_etc+"' WHERE s_id = '"+s_id+"'";
    	return insert;
    }
    public String Update_CONTINUITY(String c_name){
    	String insert = "UPDATE CONTINUITY SET c_new = 'N' WHERE c_name = '"+c_name+"'";
    	return insert;
    }
}
