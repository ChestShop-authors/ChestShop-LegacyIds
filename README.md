# ChestShop-LegacyIds

Module for [ChestShop-3](https://github.com/ChestShop-authors/ChestShop-3) to make shops work when they still use pre-1.13 item IDs on the shop signs that aren't easily translatable to the new flattening Material names.

This uses [IDConverter](https://github.com/Phoenix616/IDConverter) mappings to translate IDs in the case that no post-1.13 one can be found. It's an optional extension as such translation might lead to errors and to keep ChestShop-3 under the LGPL as IDConverter is licensed under GPL.

Downloads can be found on the [development build server](https://ci.minebench.de/job/ChestShop-LegacyIds/), currently no extra resource/project page exists.

## Config
```yaml
convert:
  # Old, now removed numeric ID values
  numeric-ids: true
  # Old, pre-1.13/flattening Material names.
  legacy-names: true
```

## License
```
ChestShop-LegacyIds
Copyright (C) 2018 Max Lee aka Phoenix616 (mail@moep.tv)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```