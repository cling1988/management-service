package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.OutletListResponse;
import com.app.model.OutletModel;
import com.app.model.ProductListResponse;
import com.app.service.ManageOutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= CommonKey.URL_OUTLET)
public class OutletController {

    @Autowired
    private ManageOutletService service;

    @PostMapping()
    public ResponseEntity<?> createOutlet(@RequestBody OutletModel model) {
        return service.createOutlet(model);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<OutletListResponse> getOutlets() {
        return service.getOutlets();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<OutletModel> getOutlet(@PathVariable Long id){

        return service.getOutlet(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOutlet(@PathVariable Long id) {
        return service.deleteOutlet(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateOutlet(@RequestBody OutletModel model, @PathVariable Long id) {

        return service.updateOutlet(model,id);
    }
}
