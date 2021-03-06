package com.buzz.electronicvoucher.data;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis.utils.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BillDataFiller extends DataFiller {

	private String reportName = "factura";

	@Override
	public String getReportName() {
		return this.reportName;
	}

	public String getReportRuc() {
		return this.reportRuc;
	}

	public void fillData(Object pk, Object voucher) throws Exception {
		// TODO Auto-generated method stub

	}

	public void save() throws Exception {
		// TODO Auto-generated method stub

	}

	public String fillReportData(Document document,
			ConcurrentHashMap<String, Object> reportParameters)
			throws Exception {
		String totalSinImpuestos = "";
		String iva0 = "0";
		String iva12 = "0";
		String ivas = "0";
		reportParameters.put("IVA_0", iva0);
		reportParameters.put("IVA_12", iva12);
		reportParameters.put("NO_OBJETO_IVA", ivas);
		NodeList nodeinfoFactura = document.getElementsByTagName("infoFactura");
		NodeList nodetotalImpuesto = document
				.getElementsByTagName("totalImpuesto");
		NodeList nodeImpuesto = document.getElementsByTagName("impuesto");
		String valor = "0";
		String valori = "0";
		for (int s = 0; s < nodeinfoFactura.getLength(); s++) {
			Node primerNodo = nodeinfoFactura.item(s);
			String fechaEmision;
			String contribuyenteEspecial;
			String obligadoContabilidad;
			String razonSocialComprador;
			String identificacionComprador;
			String sucursal;
			String importeTotal;

			String totalDescuento;

			Element primerElemento = (Element) primerNodo;
			fechaEmision = primerElemento.getElementsByTagName("fechaEmision")
					.item(0).getChildNodes().item(0).getNodeValue();

			contribuyenteEspecial = primerElemento
					.getElementsByTagName("contribuyenteEspecial").item(0)
					.getChildNodes().item(0).getNodeValue();

			obligadoContabilidad = primerElemento
					.getElementsByTagName("obligadoContabilidad").item(0)
					.getChildNodes().item(0).getNodeValue();

			razonSocialComprador = primerElemento
					.getElementsByTagName("razonSocialComprador").item(0)
					.getChildNodes().item(0).getNodeValue();

			identificacionComprador = primerElemento
					.getElementsByTagName("identificacionComprador").item(0)
					.getChildNodes().item(0).getNodeValue();

			sucursal = primerElemento
					.getElementsByTagName("dirEstablecimiento").item(0)
					.getChildNodes().item(0).getNodeValue();

			importeTotal = primerElemento.getElementsByTagName("importeTotal")
					.item(0).getChildNodes().item(0).getNodeValue();

			totalSinImpuestos = primerElemento
					.getElementsByTagName("totalSinImpuestos").item(0)
					.getChildNodes().item(0).getNodeValue();

			totalDescuento = primerElemento
					.getElementsByTagName("totalDescuento").item(0)
					.getChildNodes().item(0).getNodeValue();

			reportParameters.put("DIR_SUCURSAL", sucursal);
			reportParameters.put("FECHA_EMISION", fechaEmision);
			reportParameters.put("CONT_ESPECIAL", contribuyenteEspecial);
			reportParameters.put("LLEVA_CONTABILIDAD", obligadoContabilidad);
			reportParameters.put("RS_COMPRADOR", razonSocialComprador);
			reportParameters.put("RUC_COMPRADOR", identificacionComprador);
			reportParameters.put("SUBTOTAL", totalSinImpuestos);
			reportParameters.put("TOTAL_DESCUENTO", totalDescuento);
			reportParameters.put("VALOR_TOTAL", importeTotal);

		}
		reportParameters.put("ICE", valor);
		reportParameters.put("IVA", valor);
		for (int s = 0; s < nodetotalImpuesto.getLength(); s++) {
			Node primerNodo = nodetotalImpuesto.item(s);
			String codigo;
			Element primerElemento = (Element) primerNodo;
			codigo = primerElemento.getElementsByTagName("codigo").item(0)
					.getChildNodes().item(0).getNodeValue();
			if ("3".equals(codigo)) {
				valori = primerElemento.getElementsByTagName("valor").item(0)
						.getChildNodes().item(0).getNodeValue();

				reportParameters.put("ICE", valori);
			} else {
				reportParameters.put("IVA", valor);
			}
		}
		BigDecimal total = BigDecimal.ZERO;
		for (int s = 0; s < nodeImpuesto.getLength(); s++) {
			Node primerNodo = nodeImpuesto.item(s);
			String codigo;
			String totivas;
			Element primerElemento = (Element) primerNodo;
			codigo = primerElemento.getElementsByTagName("codigo").item(0)
					.getChildNodes().item(0).getNodeValue();

			totivas = primerElemento.getElementsByTagName("baseImponible")
					.item(0).getChildNodes().item(0).getNodeValue();
			total = new BigDecimal(totivas);
			if ("0".equals(codigo)) {
				iva0 = (total.add(new BigDecimal(iva0))).toString();
			}
		}
		reportParameters.put("IVA_0", iva0);
		reportParameters.put("IVA_12", (new BigDecimal(totalSinImpuestos)
				.add(new BigDecimal(valori))).toString());
		return this.reportName;
	}

}
