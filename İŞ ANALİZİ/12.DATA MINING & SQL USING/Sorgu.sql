
SELECT 
    COUNT(*) AS toplam_musteri_sayisi,
    COUNT(CASE 
            WHEN EXTRACT(YEAR FROM age(birth_date)) BETWEEN 20 AND 22
                 AND EXTRACT(MONTH FROM consent_date) = 6 
            THEN 1 
            ELSE NULL 
          END) AS haziran_ayinda_kayit_olan_20_22_yas_arasi_musteri_sayisi
FROM users;
