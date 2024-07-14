package com._32bit.project.cashier_system.pdf;

import com._32bit.project.cashier_system.DTO.invoice.InvoiceInfoDto;
import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.saleItem.SaleItemInfoResponseDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class PDFGenerator {
    public static ByteArrayOutputStream generateInvoicePDF(InvoiceInfoDto invoiceInfoDto) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
        Font boldFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
        Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK);

        Paragraph paragraph;

        // Business details
        paragraph = new Paragraph(invoiceInfoDto.getSalePointName(), headerFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph(invoiceInfoDto.getSalePointAddress(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph(" ", font));
        document.add(new Paragraph(" ", font));


        // Sale details
        PdfPTable saleDetailsTable = new PdfPTable(4);
        saleDetailsTable.setWidthPercentage(100);
        saleDetailsTable.setSpacingBefore(10f);
        saleDetailsTable.setSpacingAfter(10f);
        saleDetailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        saleDetailsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        saleDetailsTable.addCell(new Phrase("Tarih:", boldFont));
        saleDetailsTable.addCell(new Phrase(invoiceInfoDto.getSaleDate().toString(), font));
        saleDetailsTable.addCell(new Phrase("SAAT:", boldFont));
        saleDetailsTable.addCell(new Phrase(invoiceInfoDto.getSaleTime().toString(), font));

        saleDetailsTable.addCell(new Phrase("SATIS NO:", boldFont));
        saleDetailsTable.addCell(new Phrase(invoiceInfoDto.getSaleId().toString(), font));
        saleDetailsTable.addCell(new Phrase("KASIYER:", boldFont));
        saleDetailsTable.addCell(new Phrase(invoiceInfoDto.getTeamMemberName(), font));

        document.add(saleDetailsTable);

        document.add(new Paragraph(" ", font));

        paragraph = new Paragraph("----------------------------------------------------------------", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph(" ", font));

        PdfPTable itemsTable = new PdfPTable(2);
        itemsTable.setWidthPercentage(100);
        itemsTable.setWidths(new int[]{3, 1});
        itemsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (SaleItemInfoResponseDto item : invoiceInfoDto.getSaleItems()) {
            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);

            // Adding product brand, product name, quantity, and price
            cell.addElement(new Phrase(item.getId() + " (" + item.getQuantity() + " " + item.getUnit() +" X " + item.getPrice() + ")", font));

            // Handle discounted prices
            if (item.getDiscountedPrice() != 0.0) {
                cell.addElement(new Phrase("INDIRIM: %" + item.getDiscountRate() , font));
            }

            // Handle free items
            if (item.getForFree() != 0.0) {
                if (item.getDiscountedPrice() != 0.0)
                    cell.addElement(new Phrase("BEDAVA: " + item.getForFree() + " " + item.getUnit() + " X " + item.getDiscountedPrice(), font));
                else
                    cell.addElement(new Phrase("BEDAVA: " + item.getForFree() + " " + item.getUnit() + " X " + item.getPrice(), font));

            }

            cell.addElement(new Phrase("Total: ", font));
            cell.addElement(new Phrase(" ", font));
            itemsTable.addCell(cell);

            double total = item.getPrice() * item.getQuantity();

            PdfPCell totalCell = new PdfPCell();
            totalCell.setBorder(Rectangle.NO_BORDER);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.addElement(new Phrase(String.valueOf(total), font));
            if (item.getDiscountedPrice() != 0.0) {
                totalCell.addElement(new Phrase("-" + String.valueOf(item.getDiscountedPrice() * item.getQuantity()), font));
            }
            if (item.getForFree() != 0.0) {
                totalCell.addElement(new Phrase("-" + String.valueOf(item.getForFree() * item.getPrice()), font));
            }
            totalCell.addElement(new Phrase(String.valueOf(item.getTotal()), font));
            totalCell.addElement(new Phrase(" ", font));
            itemsTable.addCell(totalCell);
        }

        document.add(itemsTable);

        document.add(new Paragraph(" ", font));
        paragraph = new Paragraph("----------------------------------------------------------------", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);


        // Total and payment details
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(100);
        totalTable.setWidths(new int[]{3, 1});
        totalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        totalTable.addCell(new Phrase("GENEL TOPLAM", boldFont));
        totalTable.addCell(new Phrase(invoiceInfoDto.getTotal().toString(), boldFont));

        for (PaymentOfSaleDto payment : invoiceInfoDto.getPayments()) {
            totalTable.addCell(new Phrase("ODEME: " + payment.getPaymentMethod(), font));
            totalTable.addCell(new Phrase(payment.getAmount().toString(), font));
        }

        document.add(totalTable);

        document.add(new Paragraph(" ", font));
        paragraph = new Paragraph("----------------------------------------------------------------", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        // Invoice details
        document.add(new Paragraph("FATURA NO: " + invoiceInfoDto.getId(), font));
        document.add(new Paragraph("FATURA TARIHI: " + invoiceInfoDto.getExtractionDate(), font));
        document.add(new Paragraph("FATURA SAATI: " + invoiceInfoDto.getExtractionTime(), font));

        document.add(new Paragraph(" ", font));
        document.add(new Paragraph(" ", font));


        document.close();
        writer.close();

        return byteArrayOutputStream;
    }
}
