package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;

    @Autowired
    public ComercialService(ComercialDAO comercialDAO){this.comercialDAO = comercialDAO;}

    public List<Comercial> listAll(){
        return comercialDAO.getAll();
    }

    public void newComercial(Comercial comercial){
        comercialDAO.create(comercial);
    }

    public Comercial findById(int id) {
        Optional<Comercial> optionalComercial = comercialDAO.find(id);
        return optionalComercial.orElse(null);
    }

    public void replaceCliente(Comercial comercial){comercialDAO.update(comercial);}

    @Transactional
    public void delete(Integer id){
        long valorId = id.longValue();
        comercialDAO.delete(valorId);
    }
}
