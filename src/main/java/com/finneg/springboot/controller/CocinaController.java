package com.finneg.springboot.controller;

import com.finneg.springboot.model.Cocina;
import com.finneg.springboot.model.dto.BanioDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Api(value = "es un controller")
public class CocinaController {

    private List<Cocina> cocinas = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8081";
    @PostConstruct
    private void postConstruct(){
        Cocina cocina =  new Cocina();
        cocina.setCodigo("escorial");
        cocina.setNombre("Escorial 1");
        cocina.setId(0);
        cocina.setTipoHeladera("Philips");
        cocina.setTipoMesada("Marmolado");
        cocina.setTipoCocina("Industrial");

        cocinas.add(cocina);
    }

    @GetMapping(value = "/cocinas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Devuelve todas las cocinas")
    public List<Cocina> allCocinas(){
        return cocinas;
    }

    @GetMapping(value = "/cocina/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtiene una cocina por medio de un id")
    public ResponseEntity getCocina(@PathVariable int id){
        for(Cocina c: cocinas){
            if(c.getId().equals(id)){
                return ResponseEntity.ok().body(c);
            }
        }
        return (ResponseEntity) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/cocina",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "agrega una cocina")
    public List<Cocina> addCocina(@RequestBody Cocina cocina){
        cocinas.add(cocina);
        return cocinas;
    }
    @PutMapping(value = "/cocina",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "actualiza una cocina")
    public List<Cocina> modifyCocina(@RequestBody Cocina cocina){
        for(int i=0; i < this.cocinas.size(); i ++){
            if(this.cocinas.get(i).getNombre().equals(cocina.getNombre())){
                this.cocinas.set( i, cocina);
                break;
            }
        }
        return cocinas;
    }
    @DeleteMapping(value = "/cocina/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "elimina una cocina")
    public List<Cocina> deleteCocina(@PathVariable String nombre){
        cocinas.removeIf(c -> c.getNombre().equals(nombre));
        return cocinas;
    }

    @GetMapping(value = "/banios", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BanioDto> getAllBanios(){
        ResponseEntity<BanioDto[]> responseEntity = restTemplate.getForEntity(url + "/banios", BanioDto[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    @PostMapping(value = "/banio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BanioDto> addBanio(@RequestBody BanioDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BanioDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<BanioDto[]> response = restTemplate.postForEntity( url+"/banio", request , BanioDto[].class );
        return Arrays.asList(response.getBody());

    }
}
