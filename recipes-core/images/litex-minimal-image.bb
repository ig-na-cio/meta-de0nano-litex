SUMMARY = "Minimal test image"
LICENSE = "MIT"

inherit image

# SOLO busybox
IMAGE_INSTALL = "busybox"
# PACKAGE_INSTALL = "busybox"

# Formato
IMAGE_FSTYPES = "cpio.gz"

# Sin features
IMAGE_FEATURES = ""
EXTRA_IMAGE_FEATURES = ""

# Sin lenguajes extra
IMAGE_LINGUAS = ""

# Tamaño mínimo
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_ROOTFS_MAXSIZE = "65536"