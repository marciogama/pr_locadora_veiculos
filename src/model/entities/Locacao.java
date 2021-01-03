package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Locacao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date instanteLocacao;
	private Date instanteDevolucao;
	private String sede;
	private Double valorTotal;
	
	private Carro carro;
	private Cliente cliente;
	
	public Locacao() {
	}

	public Locacao(Integer id, Date instanteLocacao, Date instanteDevolucao, String sede, Double valorTotal,
			Carro carro, Cliente cliente) {
		this.id = id;
		this.instanteLocacao = instanteLocacao;
		this.instanteDevolucao = instanteDevolucao;
		this.sede = sede;
		this.valorTotal = valorTotal;
		this.carro = carro;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstanteLocacao() {
		return instanteLocacao;
	}

	public void setInstanteLocacao(Date instanteLocacao) {
		this.instanteLocacao = instanteLocacao;
	}

	public Date getInstanteDevolucao() {
		return instanteDevolucao;
	}

	public void setInstanteDevolucao(Date instanteDevolucao) {
		this.instanteDevolucao = instanteDevolucao;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Locacao [id=" + id + ", instanteLocacao=" + instanteLocacao + ", instanteDevolucao=" + instanteDevolucao
				+ ", sede=" + sede + ", valorTotal=" + valorTotal + ", carro=" + carro + ", cliente=" + cliente + "]";
	}
}
