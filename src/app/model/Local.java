package app.model;

public class Local {

	private int codigo;
	private String nome;
	private String endereco;
	private String numero;
	private String pontoReferencia;
	private String cidade;
	private String estado;
	private String latitude;
	private String longitude;

	public Local() {
	}

	public Local(int codigo, String nome, String endereco, String numero, String pontoReferencia, String cidade,
			String estado, String latitude, String longitude) {
		this.codigo = codigo;
		this.nome = nome;
		this.endereco = endereco;
		this.numero = numero;
		this.pontoReferencia = pontoReferencia;
		this.cidade = cidade;
		this.estado = estado;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Local(String nome, String endereco, String numero, String pontoReferencia, String cidade,
			String estado, String latitude, String longitude) {
		this.nome = nome;
		this.endereco = endereco;
		this.numero = numero;
		this.pontoReferencia = pontoReferencia;
		this.cidade = cidade;
		this.estado = estado;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	// getters and setters

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
