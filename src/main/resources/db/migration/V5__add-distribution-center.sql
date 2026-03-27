ALTER TABLE product ADD COLUMN distribution_center VARCHAR(255);

-- Mogi das Cruzes
UPDATE product SET distribution_center = 'Mogi das Cruzes' WHERE id IN ('p1','p4','p7','p10','p13','p16','p19');

-- Recife
UPDATE product SET distribution_center = 'Recife' WHERE id IN ('p2','p5','p8','p11','p14','p17','p20');

-- Porto Alegre
UPDATE product SET distribution_center = 'Porto Alegre' WHERE id IN ('p3','p6','p9','p12','p15','p18');