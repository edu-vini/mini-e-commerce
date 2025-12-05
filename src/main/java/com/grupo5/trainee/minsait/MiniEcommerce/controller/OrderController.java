package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.OrderCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.OrderDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ResponseExceptionDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.OrderStatus;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Order;
import com.grupo5.trainee.minsait.MiniEcommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/v1/order")
@Tag(name = "Pedidos", description = "Criar, buscar e cancelar pedidos")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Criar", description = "Cria um pedido de acordo com o id do carrinho fornecido. Endereço, e id o carrinho são obrigatórios. Desconto e frete são opicionais.")
    @ApiResponse(responseCode = "200", description = "Pedido criado!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Carrinho não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Não é possível criar pedido a partir de carrinho cancelado ou finalizado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente para o produto.", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO create(@Valid @RequestBody OrderCreateDTO orderCreateDTO){return this.orderService.create(orderCreateDTO);}

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Retorna um pedido de acordo com o id informado.")
    @ApiResponse(responseCode = "200", description = "Pedido econtrado!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO getById(@PathVariable Long id){
        return this.orderService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Retorna todos os pedidos.")
    public List<OrderDTO> getAll(){return this.orderService.getAll();}


    @DeleteMapping("/{id}/cancel")
    @Operation(summary = "Cancelar", description = "Cancela um pedido de acordo com o id.")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Não é possivel cancelar pedido após o envio!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO cancel(@PathVariable Long id){
        return this.orderService.cancel(id);
    }

    @PostMapping("/{id}/payment")
    @Operation(summary = "Pagar", description = "Marca o pedido com o status pago")
    @ApiResponse(responseCode = "200", description = "Pedido pago!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Não é possivel retroceder status do pedido!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO payment(@PathVariable Long id){
        return this.orderService.manageStatus(id, OrderStatus.PAID);
    }

    @PostMapping("/{id}/shipping")
    @Operation(summary = "Enviar", description = "Marca o pedido com o status enviado")
    @ApiResponse(responseCode = "200", description = "Pedido enviado!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Não é possivel retroceder status do pedido!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO shipping(@PathVariable Long id){
        return this.orderService.manageStatus(id, OrderStatus.SHIPPED);
    }

    @PostMapping("/{id}/receive")
    @Operation(summary = "Receber", description = "Marca o pedido com o status recebido")
    @ApiResponse(responseCode = "200", description = "Pedido recebido!", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Não é possivel retroceder status do pedido!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public OrderDTO receive(@PathVariable Long id){return this.orderService.manageStatus(id, OrderStatus.DELIVERED);}
}
