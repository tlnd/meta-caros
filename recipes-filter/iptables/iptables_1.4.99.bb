SUMMARY = "Tools for managing kernel packet filtering capabilities"
DESCRIPTION = "iptables is the userspace command line program used to configure and control network packet \
filtering code in Linux."
HOMEPAGE = "http://www.netfilter.org/"
BUGTRACKER = "http://bugzilla.netfilter.org/"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEFAULT_PREFERENCE = "-1"

PR = "r0.30"

PACKAGES =+ "${PN}-nftables ${PN}-common-dbg ${PN}-common"

DEPENDS = "libmnl libnftnl libnfnetlink libnetfilter-conntrack"
RDEPENDS_${PN} = "${PN}-common"
RDEPENDS_${PN}-nftables = "${PN}-common"
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
FILES_${PN}-common += "${libdir}/libxtables.so.* ${libdir}/xtables/ ${datadir}/xtables"
FILES_${PN}-nftables += "${sbindir}/xtables-compat* \
			 ${sbindir}/xtables-config \
			 ${sbindir}/xtables-events"

FILES_${PN}-common-dbg += "${libdir}/xtables/.debug"

SRC_URI = "git://git.netfilter.org/iptables;branch=master"
SRCREV="${AUTOREV}"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig update-alternatives

# CFLAGS += "-DDEBUG"
EXTRA_OECONF = "--with-kernel=${STAGING_INCDIR}"
PARALLEL_MAKE = ""

do_install_append() {
    rm -f ${D}${sbindir}/arptables-compat \
	  ${D}${sbindir}/ebtables-compat \
	  ${D}${sbindir}/iptables \
	  ${D}${sbindir}/iptables-restore \
	  ${D}${sbindir}/iptables-save \
	  ${D}${sbindir}/iptables-compat \
	  ${D}${sbindir}/iptables-compat-restore \
	  ${D}${sbindir}/iptables-compat-save \
	  ${D}${sbindir}/ip6tables \
	  ${D}${sbindir}/ip6tables-restore \
	  ${D}${sbindir}/ip6tables-save \
	  ${D}${sbindir}/ip6tables-compat \
	  ${D}${sbindir}/ip6tables-compat-restore \
	  ${D}${sbindir}/ip6tables-compat-save
}

PACKAGECONFIG ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'ipv6', '', d)} "
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"

ALTERNATIVE_LINK_NAME[iptables] = "${sbindir}/iptables"
ALTERNATIVE_LINK_NAME[iptables-restore] = "${sbindir}/iptables-restore"
ALTERNATIVE_LINK_NAME[iptables-save] = "${sbindir}/iptables-save"
ALTERNATIVE_LINK_NAME[ip6tables] = "${sbindir}/ip6tables"
ALTERNATIVE_LINK_NAME[ip6tables-restore] = "${sbindir}/ip6tables-restore"
ALTERNATIVE_LINK_NAME[ip6tables-save] = "${sbindir}/ip6tables-save"
ALTERNATIVE_LINK_NAME[arptables] = "${sbindir}/arptables"
ALTERNATIVE_LINK_NAME[ebtables] = "${sbindir}/ebtables"

ALTERNATIVE_${PN} = "iptables iptables-restore iptables-save ip6tables ip6tables-restore ip6tables-save"
ALTERNATIVE_PRIORITY_${PN} = "60"
ALTERNATIVE_TARGET_iptables[iptables] = "${sbindir}/xtables-multi"
ALTERNATIVE_TARGET_iptables[iptables-restore] = "${sbindir}/xtables-multi"
ALTERNATIVE_TARGET_iptables[iptables-save] = "${sbindir}/xtables-multi"
ALTERNATIVE_TARGET_iptables[ip6tables] = "${sbindir}/xtables-multi"
ALTERNATIVE_TARGET_iptables[ip6tables-restore] = "${sbindir}/xtables-multi"
ALTERNATIVE_TARGET_iptables[ip6tables-save] = "${sbindir}/xtables-multi"

ALTERNATIVE_${PN}-nftables = "iptables iptables-restore iptables-save ip6tables ip6tables-restore ip6tables-save arptables ebtables"

ALTERNATIVE_PRIORITY_${PN}-nftables  = "100"
ALTERNATIVE_TARGET_iptables-nftables[iptables] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[iptables-restore] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[iptables-save] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[ip6tables] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[ip6tables-restore] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[ip6tables-save] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[arptables] = "${sbindir}/xtables-compat-multi"
ALTERNATIVE_TARGET_iptables-nftables[ebtables] = "${sbindir}/xtables-compat-multi"

