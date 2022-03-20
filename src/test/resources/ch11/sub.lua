-- check argument's order when call a function
function my_sub(a, b)
    return a - b;
end
local res = my_sub(999, 888)
print(res) -- 111