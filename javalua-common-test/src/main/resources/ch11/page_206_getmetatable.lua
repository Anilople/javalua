-- page 206
print(getmetatable("foo"))  --> table: 0x...
print(getmetatable("bar"))  --> table: 0x...
print(getmetatable(nil))    --> nil
print(getmetatable(false))  --> nil
print(getmetatable(100))    --> nil
print(getmetatable({}))     --> nil
print(getmetatable(print()))        --> nil
