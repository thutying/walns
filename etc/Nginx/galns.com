server {

    listen       80 default;

    server_name galns.com www.galns.com;

    #root /var/www/html/wordpress/;

    #rewrite ^(.*)$ https://$host$1 permanent;

    return 301 https://$server_name$request_uri;

}


server {

    listen   443 ssl http2;

    server_name  galns.com www.galns.com;

    root   /var/www/html/wordpress/;
    
    #ssl    on;
    
    ssl_certificate     /etc/nginx/ssl/chain.galns.com;
    
    ssl_certificate_key  /etc/nginx/ssl/key.galns.com;

    ssl_session_cache shared:SSL:1m;
    
    ssl_session_timeout 10m;

    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;

    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE;
    
    ssl_prefer_server_ciphers on;

    client_max_body_size 64m;


    location / {
        root  /var/www/html/wordpress/;
        index  index.php index.html index.htm;
    }

    location ~ \.php$ {

        root /var/www/html/wordpress;
        include snippets/fastcgi-php.conf;
        fastcgi_pass unix:/run/php/php7.4-fpm.sock;
        #fastcgi_param SCRIPT_FILENAME /scripts$fastcgi_script_name;

    }

    location ~* ^/(images|img|javascript|js|css|blog|flash|media|static)/ {
        root  /var/www/html/wordpress/;
        expires  30d;
    }

    location ~* ^/(robots\.txt) {
        root  /var/www/html/wordpress/;
        expires  365d;
    }

    location ~* ^/favicon\.ico {
        root  /var/www/html/wordpress/;
        expires  365d;
    }

    location ~* ^/img/logo\.png {
        root  /var/www/html/wordpress/;
        expires  365d;
    }

    location ~ /\.ht {
        deny all;
    }
}
