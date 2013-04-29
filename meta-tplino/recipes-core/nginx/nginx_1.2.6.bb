DESCRIPTION = "Nginx is a free, open-source, high-performance HTTP server and reverse proxy, as well as an IMAP/POP3 proxy server."
HOMEPAGE = "http://wiki.nginx.org"
SECTION = "net"
PRIORITY = "optional"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0d645f970023c604645486fea08b22aa"

PR = "r0"

SRC_URI = "http://nginx.org/download/nginx-${PV}.tar.gz \
           file://allow-cross.patch \
	   file://nginx.service"

SRC_URI[md5sum] = "1350d26eb9b66364d9143fb3c4366ab6"
SRC_URI[sha256sum] = "0510af71adac4b90484ac8caf3b8bd519a0f7126250c2799554d7a751a2db388"

DEPENDS = "libpcre logrotate"

FILES_${PN} += " /usr/local/nginx "

inherit systemd

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "nginx.service"

do_configure() {
    export cross_compiling="yes"
    ${S}/configure \
    --with-cc="${HOST_PREFIX}gcc -Wl,--hash-style=gnu" \
    --sbin-path=${sbindir}/nginx \
    --pid-path=${localstatedir}/run/nginx.pid \
    --lock-path=${localstatedir}/lock/nginx.lock \
    --error-log-path=${localstatedir}/nginx/error \
    --conf-path=${sysconfdir}/nginx/nginx.conf \
    --http-log-path=${localstatedir}/nginx/access \
    --http-client-body-temp-path=${localstatedir}/lib/nginx/client_body_temp \
    --http-proxy-temp-path=${localstatedir}/lib/nginx/proxy_temp \
    --http-fastcgi-temp-path=${localstatedir}/lib/nginx/fastcgi_temp \
    --http-uwsgi-temp-path=${localstatedir}/lib/nginx/uwsgi_temp \
    --http-scgi-temp-path=${localstatedir}/lib/nginx/scgi_temp
}

do_install() {
    install -d ${D}${localstatedir}/lib/nginx
    install -d ${D}${localstatedir}/nginx
    install -d ${D}${sysconfdir}/logrotate.d

    install -m 0644 ${THISDIR}/files/nginx.logrotate ${D}${sysconfdir}/logrotate.d

    oe_runmake DESTDIR=${D} install
}
