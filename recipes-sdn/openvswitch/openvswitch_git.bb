require openvswitch.inc

DEPENDS += "virtual/kernel"

RDEPENDS_${PN}-ptest += "\
	python-logging python-syslog python-argparse python-io \
	python-fcntl python-shell python-lang python-xml python-math \
	python-datetime python-netclient python sed \
	"

S = "${WORKDIR}/git"
PV = "2.4.1+${SRCREV}"

FILESEXTRAPATHS_append := "${THISDIR}/${PN}-git:"

SRCREV = "f8184f0b6ab34f77cdd4d7c6d60d059fe952df68"
SRC_URI += "\
	git://github.com/openvswitch/ovs.git;protocol=git;branch=branch-2.4 \
	file://openvswitch-add-more-target-python-substitutions.patch \
	file://openvswitch-add-ptest-${SRCREV}.patch \
	file://run-ptest \
	file://disable_m4_check.patch \
	file://kernel_module.patch \
	"

LIC_FILES_CHKSUM = "file://COPYING;md5=5973c953e3c8a767cf0808ff8a0bac1b"

PACKAGECONFIG ?= ""
PACKAGECONFIG[dpdk] = "--with-dpdk=${STAGING_DIR_TARGET}/opt/dpdk/${TARGET_ARCH}-native-linuxapp-gcc,,dpdk,"

# Don't compile kernel modules by default since it heavily depends on
# kernel version. Use the in-kernel module for now.
# distro layers can enable with EXTRA_OECONF_pn_openvswitch += ""
# EXTRA_OECONF += "--with-linux=${STAGING_KERNEL_BUILDDIR} --with-linux-source=${STAGING_KERNEL_DIR} KARCH=${TARGET_ARCH}"

# silence a warning
FILES_${PN} += "/lib/modules"

inherit ptest

EXTRA_OEMAKE += "TEST_DEST=${D}${PTEST_PATH} TEST_ROOT=${PTEST_PATH}"

do_install_ptest() {
	oe_runmake test-install
}

do_install_append() {
	oe_runmake modules_install INSTALL_MOD_PATH=${D}
}
