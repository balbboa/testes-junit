package residencia.vendas.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import residencia.vendas.entidade.Item;
import residencia.vendas.entidade.Pedido;
import residencia.vendas.entidade.TipoItem;
import residencia.vendas.entidade.Usuario;

public class ServicoVendas {
	
//		Ao finalizar a venda, as seguintes regras devem ser seguidas. O valor do
//		frete é calculado com base no peso total de todos itens comprados: até 2 kg não é cobrado frete; acima de 2 kg e
//		abaixo de 10 kg é cobrado R$ 2,00 por kg; acima de 10 kg e abaixo de 50 kg é cobrado R$ 4,00 por kg; e acima de
//		50 kg é cobrado R$ 7,00 por kg. Caso o carrinho de compras tenha mais de 5 itens, independente do seu peso ou
//		valor, haverá um acréscimo de R$ 10,00 no frete. Carrinhos de compras que possuem mais de dois itens do
//		mesmo tipo recebem 5% de desconto apenas no valor do frete. Por fim, carrinhos de compras que custam mais
//		de R$ 500,00 recebem um desconto de 10% e mais de R$ 1000,00 recebem 20% de desconto. Vale ressaltar que
//		este desconto é aplicado somente ao valor dos itens, excluindo o valor do frete.

	Pedido fecharVenda(Usuario usuario, List<Item> itens) {
		List<TipoItem> itensTemp = new ArrayList<TipoItem>();
		Map<TipoItem, Integer> mapContagemPorTipo = new HashMap<TipoItem, Integer>();
		
		BigDecimal desconto5 = new BigDecimal("0.05");
		BigDecimal desconto10 = new BigDecimal("0.10");
		BigDecimal desconto20 = new BigDecimal("0.20");
		
		BigDecimal frete = new BigDecimal(0.0);
		BigDecimal valorPago = new BigDecimal(0.0);
		BigDecimal peso = new BigDecimal(0.0);

		for (Item item : itens) {
			valorPago = valorPago.add(item.preco());
			peso = peso.add(item.peso()).setScale(2, RoundingMode.DOWN);
			itensTemp.add(item.tipo());
		}

		frete = valorFrete(frete, peso);
		
		// Caso o carrinho de compras tenha mais de 5 itens, independente do seu peso ou
		// valor, haverá um acréscimo de R$ 10,00 no frete.
		if (itens.size() >= 5) {
			frete = frete.add(new BigDecimal(10.0));
		}

		// Carrinhos de compras que possuem mais de dois itens do
		// mesmo tipo recebem 5% de desconto apenas no valor do frete.

		boolean aplicarDesconto5NoFrete = verificarSePossuiDoisItemsDoMesmoTipoItem(itensTemp,
				mapContagemPorTipo);

		frete = desconto5NoFreteAplicado(desconto5, frete, aplicarDesconto5NoFrete);

		// Por fim, carrinhos de compras que custam mais
		// de R$ 500,00 recebem um desconto de 10%
		
		BigDecimal totalDescontoItens = descontoSeCarrrinhoCustarMaisQueCemReais(desconto10, valorPago);

		//  e mais de R$ 1000,00 recebem 20% de desconto

		totalDescontoItens = descontoSeCarrinhoCustarMaisQueMilReais(desconto20, valorPago, totalDescontoItens);

		Pedido pedido = pedidoCarrinho(usuario, itens, frete, valorPago, totalDescontoItens);

		return pedido;
	}

	private Pedido pedidoCarrinho(Usuario usuario, List<Item> itens, BigDecimal frete, BigDecimal valorPago,
			BigDecimal totalDescontoItens) {
		valorPago = valorPago.add(frete);
		valorPago = valorPago.subtract(totalDescontoItens);

		LocalDate date = LocalDate.now();
		Pedido pedido = new Pedido(usuario, itens, date, valorPago);
		return pedido;
	}

	private BigDecimal descontoSeCarrinhoCustarMaisQueMilReais(BigDecimal desconto20, BigDecimal valorPago,
			BigDecimal totalDescontoItens) {
		if (valorPago.doubleValue() >= 1000) {
			totalDescontoItens = valorPago.multiply(desconto20);
		}
		return totalDescontoItens;
	}

	private BigDecimal descontoSeCarrrinhoCustarMaisQueCemReais(BigDecimal desconto10, BigDecimal valorPago) {
		BigDecimal totalDescontoItens = new BigDecimal("0.0");
		if (valorPago.doubleValue() >= 500 && valorPago.doubleValue() < 1000) {
			totalDescontoItens = valorPago.multiply(desconto10);
		}
		return totalDescontoItens;
	}

	private BigDecimal desconto5NoFreteAplicado(BigDecimal desconto5, BigDecimal frete,
			boolean aplicarDesconto5NoFrete) {
		BigDecimal totalDescontoFrete = new BigDecimal("0.0");
		if (aplicarDesconto5NoFrete) {
			totalDescontoFrete = frete.multiply(desconto5).setScale(2, RoundingMode.DOWN);
			frete = frete.subtract(totalDescontoFrete);
		}
		return frete;
	}

	private boolean verificarSePossuiDoisItemsDoMesmoTipoItem(List<TipoItem> itensTemp,
			Map<TipoItem, Integer> mapContagemPorTipo) {
		for (int i = 0; i < itensTemp.size(); ++i) {
			TipoItem tipoItem = itensTemp.get(i);
			if (mapContagemPorTipo.containsKey(tipoItem)) {
				mapContagemPorTipo.put(tipoItem, mapContagemPorTipo.get(tipoItem) + 1);
			} else {
				mapContagemPorTipo.put(tipoItem, 1);
			}
		}

		boolean aplicarDesconto5NoFrete = false;
		for (Map.Entry<TipoItem, Integer> entry : mapContagemPorTipo.entrySet()) {
			TipoItem key = entry.getKey();
			Integer val = entry.getValue();
			if (val > 2) {
				aplicarDesconto5NoFrete = true;
			}
		}
		return aplicarDesconto5NoFrete;
	}

	private BigDecimal valorFrete(BigDecimal frete, BigDecimal peso) {
		// até 2 kg não é cobrado frete
		if (peso.doubleValue() <= 2) {
			System.out.println("Não cobrou o frete");
		} else if (peso.doubleValue() > 2 && peso.doubleValue() <= 10) {
			// acima de 2 kg e abaixo de 10 kg é cobrado R$ 2,00 por kg
			BigDecimal varFrete = new BigDecimal(2.0);
			varFrete = varFrete.multiply(peso);
			frete = frete.add(varFrete);
			
		} else if (peso.doubleValue() > 10 && peso.doubleValue() <= 50) {
			// acima de 10 kg e abaixo de 50 kg é cobrado R$ 4,00 por kg
			BigDecimal varFrete = new BigDecimal(4.0);
			varFrete = varFrete.multiply(peso);
			frete = frete.add(varFrete);
			
		} else {
			// e acima de 50 kg é cobrado R$ 7,00 por kg
			BigDecimal varFrete = new BigDecimal(7.0);
			varFrete = varFrete.multiply(peso);
			frete = frete.add(varFrete);
		}
		return frete;
	}
}
