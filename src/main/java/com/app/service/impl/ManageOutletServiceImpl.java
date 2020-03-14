package com.app.service.impl;

import com.app.model.OutletListResponse;
import com.app.model.OutletModel;
import com.app.service.ManageOutletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ManageOutletServiceImpl implements ManageOutletService {
    @Override
    public ResponseEntity<?> createOutlet(OutletModel product) {
        return null;
    }

    @Override
    public ResponseEntity<OutletListResponse> getOutlets() {
        return null;
    }

    @Override
    public ResponseEntity<OutletModel> getOutlet(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteOutlet(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<OutletModel> updateOutlet(OutletModel product, Long id) {
        return null;
    }
}
