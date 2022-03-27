local function add(a, b)
    error("cannot add")
end

ok, res = pcall(add, 111, 222)
print(ok) -- false
