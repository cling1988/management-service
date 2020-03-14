package com.app.service;

import com.app.model.OutletListResponse;
import com.app.model.OutletModel;
import com.app.model.ProductListResponse;
import com.app.model.ProductModel;
import org.springframework.http.ResponseEntity;

public interface ManageOutletService {

    public ResponseEntity<?> createOutlet(OutletModel product);

    public ResponseEntity<OutletListResponse> getOutlets();

    public ResponseEntity<OutletModel> getOutlet(Long id);

    public ResponseEntity<?> deleteOutlet(Long  id);

    public ResponseEntity<OutletModel> updateOutlet(OutletModel product, Long id);
}
