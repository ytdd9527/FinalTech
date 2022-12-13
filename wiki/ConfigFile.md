```yaml
# The language to load. Default value is en-US, and this is the only official language now.
language: en-US

# This value will be used to update the config file. Do not change it.
version: '20221204'

# Change how Slimefun machines work, Valid value:
# 0: Nothing changed, everything is like before.
# 1: All machines from this plugin will be run in async threads if they are marked as async.
# 2: All machines from this plugin will be run in async threads whether they are marked as sync or not.
# !!!Do not change this unless you know what you are doing!!!
multi-thread:
  level: 2

# Force Slimefun machines to be run in async threads if they are marked as async.
# !!!Do not change this unless you know what you are doing!!!
force-slimefun-multi-thread:
  enable: true

tweak:
  # All Slimefun machines in this collection will not be ticked twice in one Slimefun tick.
  anti-accelerate: [<SlimefunItem's Id>]
  # All Slimefun machines in this collection will be slowed while server is in low tps.
  performance-limit: [<SlimefunItem's Id>]
  # All Slimefun machines will be run in async threads.
  # !!!Do not change this unless you know what you are doing!!!
  force-async: [<SlimefunItem's Id>]

# The threshold that server will print warning info while some Slimefun machines is clicked too quickly in one Slimefun tick.
manual:
  count-threshold: 10

# The delay to set up something.
setups:
  # This will set up item value table for all Slimefun items.
  # Make sure all Slimefun items is registered.
  item-value-table:
    delay: 10
  # This will update the Block Ticker for all Slimefun items.
  # Make sure all Slimefun items is registered with Block Ticker.
  slimefun-machine:
    delay: 20

# If a Slimefun machines is banned, it's Block Ticker will be disabled.
super-ban: false

# this is random generated, and please do not change this.
seed: 8109447790222233271

# Do not change this.
debug-mode: false

# While this plugin is updated, whether to update config file during server start.
update:
  # Set this to false will disable all the function of config file update system.
  enable: true
  # Whether to update language file.
  # If you are using an unofficial language file, it will be recommended to set it to false.
  language: true
```
