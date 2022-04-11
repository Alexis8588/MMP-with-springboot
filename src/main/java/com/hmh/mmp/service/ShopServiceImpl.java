package com.hmh.mmp.service;

import com.hmh.mmp.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{
    private final ShopRepository sr;
}
