package carti;



class Carti {

	private String autor;
	private String titlu;
	private String nr_exemplare;
	public Carti()
	{
		
	}
	public Carti(String autor, String titlu, String nr_exemplare){
		this.autor=autor;
		this.titlu=titlu;
		this.nr_exemplare=nr_exemplare;
	}
	
	public void setAutor(String autor){
		this.autor=autor;
	}
	
	public void setTitlu(String titlu){
		this.titlu=titlu;
	}
	
	public void setNrEx(String nr_exemplare){
		this.nr_exemplare=nr_exemplare;
	}
	
	public String getAutor()
	{
		return autor;
	}
	
	public String getTitlu()
	{
		return titlu;
	}
	
	public String getNrEx()
	{
		return nr_exemplare;
	}

}
