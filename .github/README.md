<img src="https://github.com/T145/tbone/blob/master/src/main/resources/assets/tbone/icon.jpg" align="right" width="180px"/>

# TBone

[![downloads](http://cf.way2muchnoise.eu/full_tbone_downloads.svg)](https://minecraft.curseforge.com/projects/tbone)
[![versions](http://cf.way2muchnoise.eu/versions/tbone.svg)](https://minecraft.curseforge.com/projects/tbone)
<br />
[![packs](http://cf.way2muchnoise.eu/packs/tbone.svg)](https://minecraft.curseforge.com/projects/tbone)

<br />

***

**_Table of Contents_**

1. [Dependencies](https://github.com/T145/tbone#dependencies)
2. [Workspace Setup](https://github.com/T145/tbone#workspace-setup)
3. [Project License](https://github.com/T145/tbone#license)
4. [Dev Support](https://github.com/T145/tbone#support)
5. [Contributing](https://github.com/T145/tbone/blob/master/.github/CONTRIBUTING.md)

---

## Workspace Dependencies

> *([First-Time Git Setup](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup))*

### Windows

#### Using [Scoop](https://github.com/lukesampson/scoop/blob/master/README.md) *(Recommended)*
```bash
scoop bucket add java
scoop bucket add versions
scoop install git ojdkbuild8 gradle4
```

#### Using [Chocolatey](https://chocolatey.org/install)
```bash
choco install git
choco install jdk8
choco install gradle
```

### OSX

#### Using [Homebrew](https://brew.sh/)
```bash
# Git should be automatically installed alongside
# Homebrew through Apple's Command Line utilities
brew cask install java
brew install gradle
```

---

## Workspace Setup

### Eclipse
```bash
gradle setupEclipseWorkspace
gradle eclipse
```

Next, you'll need to install the [EditorConfig plugin](https://github.com/ncjones/editorconfig-eclipse#readme).
Navigate to `Help > Eclipse Marketplace`, and search for `editorconfig`.
There should only be one result: install it and you're all set.

### IntelliJ IDEA

```bash
gradle setupDecompWorkspace
gradle idea
```
> Be sure IDEA recognizes the `src/api/java` directory!

---

## License

Mod source code is licensed under the [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
The actual workspace license is located in this project.
To use any mod assets, you may contatct [myself](https://github.com/T145) or the original creator for permission.

---

## Financial Support

<div align="center">

**Patreon**: [patreon.com/user=152139](https://www.patreon.com/user?u=152139)
</div>

<div align="center">

**Paypal**: *Check the top right of the [CurseForge page](https://minecraft.curseforge.com/projects/tbone)!*
</div>

<div align="center">

**Cryptocurrency**: [Check here!](https://github.com/T145/tbone/blob/master/.github/CRYPTO_ADDRESSES.md)
</div>
