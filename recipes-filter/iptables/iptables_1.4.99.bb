SUMMARY = "Tools for managing kernel packet filtering capabilities"
DESCRIPTION = "iptables is the userspace command line program used to configure and control network packet \
filtering code in Linux."
HOMEPAGE = "http://www.netfilter.org/"
BUGTRACKER = "http://bugzilla.netfilter.org/"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEFAULT_PREFERENCE = "-1"

DEPENDS = "libmnl libnftnl"
RRECOMMENDS_${PN} = "kernel-module-x-tables \
                     kernel-module-ip-tables \
                     kernel-module-iptable-filter \
                     kernel-module-iptable-nat \
                     kernel-module-nf-defrag-ipv4 \
                     kernel-module-nf-conntrack \
                     kernel-module-nf-conntrack-ipv4 \
                     kernel-module-nf-nat \
                     kernel-module-ipt-masquerade \
		     kernel-module-nft-compat"
FILES_${PN} =+ "${libdir}/xtables/ ${datadir}/xtables"
FILES_${PN}-dbg =+ "${libdir}/xtables/.debug"

SRC_URI = "git://git.netfilter.org/iptables;branch=master"
SRCREV="${AUTOREV}"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

# CFLAGS += "-DDEBUG"
EXTRA_OECONF = "--with-kernel=${STAGING_INCDIR}"
PARALLEL_MAKE = ""

PACKAGECONFIG ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'ipv6', '', d)} "
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"

